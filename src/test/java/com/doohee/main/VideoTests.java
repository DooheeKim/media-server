package com.doohee.main;

import com.doohee.mediaserver.dto.*;
import com.doohee.mediaserver.entity.Exposure;
import com.doohee.mediaserver.jwt.JwtFilter;
import com.doohee.mediaserver.service.UserService;
import com.doohee.mediaserver.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAsync
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 돌릴때 클래스는 한번만 만들기위해(BeforeAll 사용) 필요
@Transactional //테스트마다 롤백시키기 위해 필요
public class VideoTests {
    @Autowired
    private Environment environment;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;
    //사인업 -> 로그인 까지 해야됨..

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
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

        userService.signup(userDto1); // 회원가입
    }

    @Test
    public void storeVideoServiceTest(){
        userService.login(LoginDto.builder()
                .username(userDto1.getUsername())
                .password(userDto1.getPassword())
                .build()
        );//로그인
        MultipartFile videoFile;
        MultipartFile thumbnailFile;
        Path videoPath = Paths.get(environment.getProperty("storage.path"), "test_video_id", "test_video_id.mp4");
        Path imagePath = Paths.get(environment.getProperty("storage.path"), "test_video_id", "test_video_id.png");

        try{
            videoFile = new MockMultipartFile(
                    "file",
                    "test_video_id.mp4",
                    MediaType.MULTIPART_MIXED_VALUE,
                    Files.readAllBytes(videoPath));

            thumbnailFile = new MockMultipartFile(
                    "file",
                    "test_video_id.png",
                    MediaType.IMAGE_PNG_VALUE,
                    Files.readAllBytes(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        VideoUploadDto videoUploadDto = new VideoUploadDto("hello", "testFile", Exposure.PUBLIC);

        File dir = new File(environment.getProperty("storage.path"));
        File[] files = dir.listFiles();

        try{

            VideoUploadResultDto result = videoService.storeVideo(videoUploadDto, videoFile, thumbnailFile);
            assertThat(result.getTitle()).isEqualTo("hello"); //제목 제대로 들어갔는지
            assertThat(result.getDescription()).isEqualTo("testFile"); //설명 제대로 들어갔는이 확인
            assertThat(dir.listFiles().length).isEqualTo(files.length+1); //폴더 하나 더 생겼나 확인
        } catch (Exception e){
            fail("파일 처리 오류");
        }
    }
    @Test
    public void storeVideoTest() throws Exception{
        TokenDto tokenDto = userService.login(LoginDto.builder()
                .username(userDto1.getUsername())
                .password(userDto1.getPassword())
                .build()
        );//로그인
        MockMultipartFile videoFile;
        MockMultipartFile thumbnailFile;
        Path videoPath = Paths.get(environment.getProperty("storage.path"), "test_video_id", "test_video_id.mp4");
        Path imagePath = Paths.get(environment.getProperty("storage.path"), "test_video_id", "test_video_id.png");

        try{
            videoFile = new MockMultipartFile(
                    "video",
                    "test_video_id.mp4",
                    MediaType.MULTIPART_MIXED_VALUE,
                    Files.readAllBytes(videoPath));

            thumbnailFile = new MockMultipartFile(
                    "thumbnail",
                    "test_video_id.png",
                    MediaType.IMAGE_PNG_VALUE,
                    Files.readAllBytes(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VideoUploadDto videoUploadDto = new VideoUploadDto("hello", "testfile", Exposure.PUBLIC);
        MockMultipartFile body = new MockMultipartFile("body", "body", "application/json", objectMapper.writeValueAsString(videoUploadDto).getBytes());

        File dir = new File(environment.getProperty("storage.path"));
        File[] files = dir.listFiles();

//        MvcResult result = this.mockMvc.perform(multipart("/videos/")
//                        .file(body)
//                        .file(file)
//                .header(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+tokenDto.getToken())
//                .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA, MediaType.MULTIPART_MIXED))
//                .andExpect(status().isOk())
//                .andReturn();

        MvcResult result = this.mockMvc.perform(multipart("/videos/")
                        .file(body)
                        .file(videoFile)
                        .file(thumbnailFile)
                .header(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+tokenDto.getToken())
                .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA, MediaType.MULTIPART_MIXED))
                .andExpect(status().isOk())
                .andReturn();
    }

}

