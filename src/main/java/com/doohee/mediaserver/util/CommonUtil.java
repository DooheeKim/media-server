package com.doohee.mediaserver.util;


import com.doohee.mediaserver.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Locale;
import java.util.Set;

@Component
public class CommonUtil {
    private static Environment environment;
    private static final Set<String> VIDEO_EXT = Set.of("mp4", "avi", "mkv", "mpg", "mpeg", "ts", "wmv", "mov", "webm");
    private static final Set<String> IMAGE_EXT = Set.of("jpg", "jpeg", "png", "bmp");
    @Autowired
    private Environment wiringEnvironment; //static 변수를 component에서 활용하기위한 우회변수

    @PostConstruct
    public void init(){
        this.environment = wiringEnvironment;
    }
    public static Path videoPathFromId(String videoId, String extension){
        return Paths.get(getStoragePath(), //기본 저장경로
                videoId, //새로 만들 폴더경로
                videoId+"."+extension);//파일명
    }
    public static String thumbnailInBase64(String videoId, String thumbnailExtension){
        Path thumbnailPath = Paths.get(getStoragePath(), videoId, videoId+"."+thumbnailExtension);
        byte[] thumbnail;

        try{
            thumbnail = Files.readAllBytes(thumbnailPath);
        } catch (IOException e) {
            throw new StorageException(e);
        }
        return Base64.getEncoder().encodeToString(thumbnail);
    }

    public static boolean isVideo(String extension){
        return VIDEO_EXT.contains(extension.toLowerCase());
    }
    public static boolean isImage(String extension){
        return IMAGE_EXT.contains(extension.toLowerCase());
    }
    public static String getStoragePath(){
        return environment.getProperty("storage.path");
    }
}
