package com.doohee.main;

import com.doohee.mediaserver.entity.Exposure;
import com.doohee.mediaserver.dto.VideoUploadData;
import com.doohee.mediaserver.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VideoTests {
    @Autowired
    Environment environment;

    @Autowired
    VideoService videoService;

    @Test
    public void storeVideo(){
        MultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, this is doohee".getBytes());

        VideoUploadData videoUploadData = new VideoUploadData(file, Exposure.PRIVATE, "hello", "testfile");

        File dir = new File(environment.getProperty("storage.path"));
        File[] files = dir.listFiles();

        int result = videoService.storeVideo(videoUploadData);
        assertThat(result).isEqualTo(0); //리턴값이 0인지 확인

        assertThat(dir.listFiles().length).isEqualTo(files.length+1); //폴더 하나 더 생겼나 확인
    }
}
