package com.doohee.main;

import com.doohee.mediaserver.dto.AuthorityDto;
import com.doohee.mediaserver.dto.LoginDto;
import com.doohee.mediaserver.dto.UserDto;
import com.doohee.mediaserver.entity.User;
import com.doohee.mediaserver.exception.DuplicateMemberException;
import com.doohee.mediaserver.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 돌릴때 클래스는 한번만 만들기위해(BeforeAll 사용) 필요
@Transactional //테스트마다 롤백시키기 위해 필요
public class UserTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

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
    }

    @Test
    public void signingUpServiceTest() throws Exception{
        UserDto signedUp = userService.signup(userDto1);
        assertThat(signedUp).isNotNull();
        assertThat(signedUp.getNickname()).isEqualTo("test1_nick");

        //계정 중복 생성시 에러처리
        assertThatThrownBy(()->{userService.signup(userDto1);})
                .isInstanceOf(DuplicateMemberException.class);

    }

    @Test
    public void signingUpTest() throws Exception{
        String body = objectMapper.writeValueAsString(userDto1);

        this.mockMvc.perform(post("/users/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{username:test1_name}"));

        body = objectMapper.writeValueAsString(userDto2);
        this.mockMvc.perform(post("/users/signup")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{username:test2_name}"));
    }

    @Test
    public void loginTest() throws Exception {
        userService.signup(userDto1);//가입
        LoginDto properLoginDto = LoginDto.builder()
                .username(userDto1.getUsername())
                .password(userDto1.getPassword())
                .build();
        String body = objectMapper.writeValueAsString(properLoginDto);
        MvcResult result = this.mockMvc.perform(post("/users/login")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        // jwt token의 regex pattern을 보유하고 있는지 확인
        assertThat(jsonObject.getString("token")).containsPattern("^[\\W+]*.[\\W+]*.[\\W+]*");
    }

}
