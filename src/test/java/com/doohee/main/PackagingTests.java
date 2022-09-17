package com.doohee.main;

import com.doohee.mediaserver.service.PackagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@EnableAsync
@SpringBootTest
public class PackagingTests {
    @Autowired
    PackagingService packagingService;
    @Autowired
    Environment environment;

//    @Test
    public void videoEncodingTest(){
//        동영상 인코딩 테스트라 시간이 너무 오래걸려서 평소에는 비활성화 해두었습니다
        Path testVideoPath = Paths.get(environment.getProperty("storage.path"),"org_video_cut.mp4");
        CompletableFuture result = packagingService.encodeToMultiResolution(testVideoPath);
        try{
            assertThat(result.get()).isEqualTo(0);
        }catch(InterruptedException | ExecutionException e){
            fail("An Error Occured in EncodingTest");
        }
    }
    @Test
    public void packagingScriptTest(){
        String testVideoId = "test_video_id";
        String testPackagingCommand = packagingService.createPackagingCommand(testVideoId);
        System.out.println(testPackagingCommand);
        assertThat(testPackagingCommand.length()).isGreaterThan(environment.getProperty("command.package").length());//16byte 키값이 들어가서 바꾼게 더 길어짐
        assertThat(testPackagingCommand).isNotEqualTo(packagingService.createPackagingCommand(testVideoId));//실행할 때 마다 달라지는 지(랜덤키가 제댜로 만들어지는지)
    }

    @Test
    public void videoPackagingTest(){
        String testVideoId = "test_video_id"; //실제로 존재하는 폴더 이름
        CompletableFuture result = packagingService.encryptAndPackaging(testVideoId);
        try{
            assertThat(result.get()).isEqualTo(0); //변환 성공
        }catch(InterruptedException | ExecutionException e){
            fail("An Error Occured in PackagingTest");
        }
    }


}
