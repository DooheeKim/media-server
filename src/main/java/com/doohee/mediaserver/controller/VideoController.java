package com.doohee.mediaserver.controller;

import com.doohee.mediaserver.dto.VideoAbstractDto;
import com.doohee.mediaserver.dto.VideoUploadDto;
import com.doohee.mediaserver.dto.VideoUploadResultDto;
import com.doohee.mediaserver.entity.Video;
import com.doohee.mediaserver.service.PackagingService;
import com.doohee.mediaserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    Environment environment;

    @Autowired
    VideoService videoService;
    @Autowired
    PackagingService packagingService;

    @GetMapping("/dash/{videoId}/{filename}")
    public ResponseEntity<Resource> getVideoWithDash(
            @PathVariable("videoId") String videoId,
            @PathVariable("filename") String filename){
        return ResponseEntity.ok(videoService.getDashVideo(videoId, filename));
    }
    @GetMapping("/dash/{videoId}/{filepath}/{filename}")
    public ResponseEntity<Resource> getVideoWithDash(
            @PathVariable("videoId") String videoId,
            @PathVariable("filepath") String filepath,
            @PathVariable("filename") String filename){
        return ResponseEntity.ok(videoService.getDashVideo(videoId, filepath+"/"+filename));
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VideoUploadResultDto> uploadVideoFile(
            @RequestPart(value = "body", required = true) VideoUploadDto info,
            @RequestPart(value = "video", required = true) MultipartFile videoFile,
            @RequestPart(value = "thumbnail", required = true) MultipartFile thumbnail){
        VideoUploadResultDto videoUploadResultDto = videoService.storeVideo(info, videoFile, thumbnail);
        packagingService.encodeAndPackageVideo(videoUploadResultDto);
        return ResponseEntity.ok(videoUploadResultDto);
    }
    @GetMapping(value = "/")
    public ResponseEntity<List<VideoAbstractDto>> listVideos(){
        return ResponseEntity.ok(videoService.loadVideoList());
    }



}
