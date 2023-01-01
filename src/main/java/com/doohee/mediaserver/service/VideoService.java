package com.doohee.mediaserver.service;

import com.doohee.mediaserver.dto.VideoAbstractDto;
import com.doohee.mediaserver.dto.VideoUploadDto;
import com.doohee.mediaserver.dto.VideoUploadResultDto;
import com.doohee.mediaserver.entity.Exposure;
import com.doohee.mediaserver.entity.User;
import com.doohee.mediaserver.entity.Video;
import com.doohee.mediaserver.entity.VideoStatus;
import com.doohee.mediaserver.exception.NoUsernameException;
import com.doohee.mediaserver.exception.StorageException;
import com.doohee.mediaserver.exception.UnsupportedExtensionException;
import com.doohee.mediaserver.repository.UserRepository;
import com.doohee.mediaserver.repository.VideoRepository;
import com.doohee.mediaserver.util.SecurityUtil;
import com.doohee.mediaserver.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class VideoService {
    @Autowired
    Environment environment;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    UserRepository userRepository;
    private String getFileExtension(String fileName){
        //파일 이름으로부터 확장자만 분리, 반환
        if (fileName.isEmpty() || !fileName.contains(".")){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    public Resource getDashVideo(String videoId, String filename){
        //Dash 특성상 파일명을 요청하므로 파일명이 따로 필요
        if(filename.isEmpty() || !(filename.endsWith(".mpd") || filename.endsWith(".m4s") || filename.endsWith(".mp4"))){
            //파일 확장자가 적절한지 확인
            throw new UnsupportedExtensionException("지원하지 않는 확장자 입니다.");
        }
        String filenameWithoutExtension = filename.substring(0, filename.length()-4);
        // 파일명과 같은 폴더가 존재하는지 확인(해당 폴더 안에 결과물이 들어가기로 약속)
        Path path = Paths.get(environment.getProperty("storage.path"),videoId, "DASH", filename);
        if(!Files.exists(path.getParent())){//해당 경로가 존재하지 않으면 --> 없는 파일
            throw new StorageException("존재하지 않는 파일입니다");
        }
        Resource resource = new FileSystemResource(path);
        return resource;
    }
    @Transactional
    public VideoUploadResultDto storeVideo(VideoUploadDto videoUploadDto, MultipartFile videoFile, MultipartFile thumbnailFile) {
        UUID videoId = UUID.randomUUID();
        String orgVideoFilename = videoFile.getOriginalFilename();
        String username = SecurityUtil.getCurrentUsername();
        String videoExtension = getFileExtension(orgVideoFilename);
        //비디오 관련

        String thumbnailExtension = getFileExtension(thumbnailFile.getOriginalFilename());
        //썸네일 관련
        if(username==null){
            throw new NoUsernameException("존재하지 않는 유저입니다");
        }
        if(!CommonUtil.isVideo(videoExtension)){
            throw new UnsupportedExtensionException("지원하지 않는 비디오 확장자입니다");
        }
        if(!CommonUtil.isImage(thumbnailExtension)){
            throw new UnsupportedExtensionException("지원하지 않는 이미지 확장자입니다");
        }
        User user = userRepository.findByUsername(username);
        Path videoStorePath = CommonUtil.videoPathFromId(videoId.toString(), videoExtension);
        Path thumbnailStorePath = CommonUtil.videoPathFromId(videoId.toString(), thumbnailExtension);


        try{
            Files.createDirectory(videoStorePath.getParent());
        }catch(IOException e){
            //대부분 이미 존재하는 폴더
            e.printStackTrace();
            throw new StorageException("이미 존재하는 파일입니다");
        }
        try{
            videoFile.transferTo(videoStorePath);
        }catch(IOException e){
            e.printStackTrace();
            throw new StorageException("비디오 파일 저장에 실패하였습니다");
        }
        try{
            thumbnailFile.transferTo(thumbnailStorePath);
        }catch(IOException e){
            e.printStackTrace();
            throw new StorageException("썸네일 저장에 실패하였습니다");
        }

        Video video = Video.builder()
                .videoId(videoId.toString())
                .title(videoUploadDto.getTitle())
                .description(videoUploadDto.getDescription())
                .status(VideoStatus.UPLOADED)
                .exposure(videoUploadDto.getExposure())
                .uploadedDate(LocalDateTime.now())
                .uploader(user)
                .extension(videoExtension)
                .build();
        Video videoSaved = videoRepository.save(video);



        return VideoUploadResultDto.builder()
                .title(videoSaved.getTitle())
                .description(videoSaved.getDescription())
                .username(videoSaved.getUploader().getUsername())
                .videoId(videoSaved.getVideoId())
                .extension(videoSaved.getExtension())
                .build();
    }
//    public List<VideoAbstractDto> loadVideoList(){
//        //일단은 exposure가 public인 동영상 전부 리턴하게, 추후 업데이트
//        List<VideoAbstractDto> result = new ArrayList<>();
//        List<Video> videoList = videoRepository.findByStatusOrderByUploadedDate(Exposure.PUBLIC);
//        for(Video video: videoList){
//            result.add(VideoAbstractDto.builder()
//                            .videoId(video.getVideoId())
//                            .title(video.getTitle())
//                            .uploader(video.getUploader().getNickname())
//                            .description(video.getDescription())
//                            .thumbnail(thumbnailInBase64(video.getVideoId(), video.getThumbnailExtension()))
//                    .build());
//        }
//        return result;
//    };


}
