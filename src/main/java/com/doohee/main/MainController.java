package com.doohee.main;

import com.doohee.main.dto.VideoUploadData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class MainController {
    @Autowired
    Environment environment;

    @GetMapping("/dashVideo/{filename}")
    public ResponseEntity<Resource> getVideoWithDash(@PathVariable String filename){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(filename.isEmpty() || !filename.endsWith(".mpd") || !filename.endsWith(".m4s")){
            //파일 확장자가 적절한지 확인
            return new ResponseEntity<Resource>(null, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
        }
        String filenameWithoutExtension = filename.substring(0, filename.length()-4);
        // 파일명과 같은 폴더가 존재하는지 확인(해당 폴더 안에 결과물이 들어가기로 약속)
        Path path = Paths.get(environment.getProperty("storage.path") + filenameWithoutExtension);
        if(!Files.exists(path)){//해당 경로가 존재하지 않으면 --> 없는 파일
            return new ResponseEntity<Resource>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Resource resource = new FileSystemResource(path+filename);

        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/dashVideo")
    public ResponseEntity<String> uploadVideoFile(@RequestBody VideoUploadData info){
        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<String>("",httpHeaders, HttpStatus.OK);
    }

}
