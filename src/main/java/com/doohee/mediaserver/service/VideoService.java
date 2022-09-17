package com.doohee.mediaserver.service;

import com.doohee.mediaserver.dto.VideoUploadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class VideoService {
    @Autowired
    Environment environment;
    private String getFileExtension(String fileName){
        //파일 이름으로부터 확장자만 분리, 반환
        if (fileName.isEmpty() || !fileName.contains(".")){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    public int storeVideo(VideoUploadData videoUploadData){
        UUID videoId = UUID.randomUUID();
        String org_filename = videoUploadData.getFile().getOriginalFilename();
        Path storePath = Paths.get(environment.getProperty("storage.path"), //기본 저장경로
                videoId.toString(), //새로 만들 폴더경로
                videoId.toString()+"."+getFileExtension(org_filename)//파일명
        );

        try{
            Files.createDirectory(storePath.getParent());
        }catch(IOException e){
            //대부분 이미 존재하는 폴더
            e.printStackTrace();
            return -1;
        }
        try{
            videoUploadData.getFile().transferTo(storePath);
        }catch(IOException e){
            System.out.println("File 저장 실패");
            e.printStackTrace();
            return -2;
        }
        return 0;
    }
}
