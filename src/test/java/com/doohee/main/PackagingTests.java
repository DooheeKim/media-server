package com.doohee.main;

import com.doohee.mediaserver.dto.VideoUploadResultDto;
import com.doohee.mediaserver.entity.VideoKey;
import com.doohee.mediaserver.repository.KeyRepository;
import com.doohee.mediaserver.service.PackagingService;
import com.doohee.mediaserver.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

//    @Autowired
//    KeyRepository keyRepository;

//    @Test
    public void videoEncodingTest(){
//        동영상 인코딩 테스트라 시간이 너무 오래걸려서 평소에는 비활성화 해두었습니다
        Path testVideoPath = Paths.get(environment.getProperty("storage.path"),"org_video_cut.mp4");
        CompletableFuture result = packagingService.encodeToMultiResolution(testVideoPath);
        try{
            assertThat(result.get()).isEqualTo(0);
        }catch(InterruptedException | ExecutionException e){
            fail("An Error Occurred in EncodingTest");
        }
    }
    @Test
    public void packagingScriptTest(){
        String testVideoId = "test_video_id";
        String testPackagingCommand = packagingService.createPackagingCommand(testVideoId);
        System.out.println(testPackagingCommand);
        assertThat(testPackagingCommand.length()).isGreaterThan(environment.getProperty("command.package").length());//16byte 키값이 들어가서 바꾼게 더 길어짐
        assertThat(testPackagingCommand).isNotEqualTo(packagingService.createPackagingCommand(testVideoId));//실행할 때 마다 달라지는 지(랜덤키가 제대로 만들어지는지)
    }

    @Test
    public void videoPackagingTest(){
        String testVideoId = "test_video_id"; //실제로 존재하는 폴더 이름
        CompletableFuture result = packagingService.encryptAndPackaging(testVideoId);
        try{
            assertThat(result.get()).isEqualTo(0); //변환 성공
        }catch(InterruptedException | ExecutionException e){
            fail("An Error Occurred in PackagingTest");
        }
    }

    @Test
    public void videoEncodingAndPackagingTest(){
        String testVideoId = "test_video_id";
        //--------기존에 있던 인코딩 폴더들 제거--------
        File encoded = new File(environment.getProperty("storage.path")+"/"+testVideoId, "encoded");
        File dash = new File(environment.getProperty("storage.path")+"/"+testVideoId, "DASH");

        deleteDir(encoded);
        deleteDir(dash);
        encoded.mkdirs();
        dash.mkdirs();
        //---------------------------------------

//        System.out.println(CommonUtil.videoPathFromId(testVideoId, "mp4"));
        VideoUploadResultDto videoUploadResultDto = VideoUploadResultDto.builder()
                .videoId(testVideoId)
                .extension("mp4")
                .build();
        CompletableFuture result = packagingService.encodeAndPackageVideo(videoUploadResultDto);
        try{
            assertThat(result.get()).isEqualTo(0); //변환 성공
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
            fail("An Error Occurred in Encoding and Packaging Test");
        }
//        VideoKey videoKey = keyRepository.findAll().get(0);
//        System.out.println("insert into video_key (video_id, key_id_fhd, key_fhd, key_id_hd, key_hd, key_id_sd, key_sd,key_id_nhd, key_nhd, key_id_audio, key_audio) values "+
//                "("+
//                "'"+videoKey.getVideoId()+"'"+", "+
//                "'"+videoKey.getKeyIdFhd()+"'"+", "+
//                "'"+videoKey.getKeyFhd()+"'"+", "+
//                "'"+videoKey.getKeyIdHd()+"'"+", "+
//                "'"+videoKey.getKeyHd()+"'"+", "+
//                "'"+videoKey.getKeyIdSd()+"'"+", "+
//                "'"+videoKey.getKeySd()+"'"+", "+
//                "'"+videoKey.getKeyIdNhd()+"'"+", "+
//                "'"+videoKey.getKeyNhd()+"'"+", "+
//                "'"+videoKey.getKeyIdAudio()+"'"+", "+
//                "'"+videoKey.getKeyAudio()+"'"+", "+
//                ")");
    }

    private void deleteDir(File file){
        File[] contents = file.listFiles();
        if(contents != null){
            for(File f : contents){
                if(! Files.isSymbolicLink(f.toPath())){
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

}
