package com.doohee.main;

import com.doohee.mediaserver.dto.*;
import com.doohee.mediaserver.jwt.JwtFilter;
import com.doohee.mediaserver.repository.KeyRepository;
import com.doohee.mediaserver.service.KeyService;
import com.doohee.mediaserver.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 돌릴때 클래스는 한번만 만들기위해(BeforeAll 사용) 필요
@Transactional //테스트마다 롤백시키기 위해 필요
public class KeyTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    KeyService keyService;

    @Autowired
    KeyRepository keyRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    UserDto userDto1;
    UserDto userDto2;
    @BeforeAll
    @Sql({"/data.sql"})
    public void setUpBefore() throws Exception{
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("data.sql"));
        Set<AuthorityDto> authorityDtoSet = new HashSet<AuthorityDto>();
        authorityDtoSet.add(new AuthorityDto("ROLE_USER"));

        userDto1 = UserDto.builder()
                .username("test1_name")
                .password("test1_pass")
                .nickname("test1_nick")
                .authorityDtoSet(authorityDtoSet)
                .build();
        userDto2 = UserDto.builder()
                .username("test2_name")
                .password("test2_pass")
                .nickname("test2_nick")
                .authorityDtoSet(authorityDtoSet)
                .build();

        userService.signup(userDto1); // 회원가입
    }

    @Test
    public void lengthOfBytes(){
        String bytes = keyService.createNBytesHex(16);
        assertThat(bytes.length()).isEqualTo(32);
        assertThat(bytes).isNotEqualTo(keyService.createNBytesHex(16)); //새로 만들때마다 달라지는지 테스트
    }
    @Test
    public void createKeysTest(){
        VideoKeyDto videoKeyDto = keyService.createKeys("test_video_id");
        //16bytes 키가 제대로 만들어졌는지 테스트
        int byteLength = 16;
        int stringLength = byteLength*2;
        assertThat(videoKeyDto.getKeyIdFhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyFhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdHd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyHd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdSd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeySd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdNhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyNhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdAudio().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyAudio().length()).isEqualTo(stringLength);

        assertThat(videoKeyDto).isNotEqualTo(keyService.createKeys("test_video_id"));
        //새로 만들때마다 랜덤값으로 제대로 만들어지는 지 테스트
    }

    @Test
    public void saveKeyTest(){
        keyRepository.deleteAll();
        assertThat(keyRepository.count()).isEqualTo(0);
        keyService.saveKeys(keyService.createKeys("test_video_id1"));
        assertThat(keyRepository.count()).isEqualTo(1);
        keyService.saveKeys(keyService.createKeys("test_video_id2"));
        assertThat(keyRepository.count()).isEqualTo(2);
    }

    @Test
    public void getKeyTest() throws Exception{
        TokenDto tokenDto = userService.login(LoginDto.builder()
                .username(userDto1.getUsername())
                .password(userDto1.getPassword())
                .build()
        );//로그인

        MvcResult result = this.mockMvc.perform(get("/keys/test_video_id_fixed")
                        .header(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+tokenDto.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content.length()).isGreaterThan(32*10);
    }
}
