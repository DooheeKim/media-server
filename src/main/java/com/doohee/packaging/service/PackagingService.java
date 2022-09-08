package com.doohee.packaging.service;

import com.doohee.common.Exception.NoVideoKeyExistsException;
import com.doohee.key.dto.VideoKeyDto;
import com.doohee.key.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class PackagingService {
    @Autowired
    Environment environment;

    @Autowired
    KeyService keyService;

    @Async
    public CompletableFuture<Integer> encodeToMultiResolution(Path videoFilePath){
        int exitVal = -1;
        Path encodedVideoPath = Paths.get(videoFilePath.getParent().toString(), "encoded");
        String command = String.format(environment.getProperty("command.encode"),
                videoFilePath,
                encodedVideoPath,//1080p
                encodedVideoPath,//720p
                encodedVideoPath,//480p
                encodedVideoPath// 360p
        );//FFMPEG 커맨드 작성
        String[] commandSplit = command.split(" ");
        ProcessBuilder processBuilder = new ProcessBuilder(commandSplit);
        try{
            Files.createDirectory(encodedVideoPath);
        }catch(IOException e){
            //대부분 이미 존재하는 폴더
        }

        try{
            Process process = processBuilder.start();
            exitVal = process.waitFor();
        } catch(IOException e){
            //실패시 db에 상태 업데이트 하는 기능 추가하면 좋을듯
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            return CompletableFuture.completedFuture(exitVal);
        }

    }
    public String createPackagingCommand(String videoId) throws IllegalArgumentException {
        Path videoFolder = Paths.get(environment.getProperty("storage.path"), videoId);
        VideoKeyDto videoKeyDto = keyService.createKeys(videoId);
        if(videoKeyDto==null){
            throw new IllegalArgumentException();
        }
        String command = String.format(environment.getProperty("command.package"),
                videoFolder,
                videoKeyDto.getKeyIdFhd(), videoKeyDto.getKeyFhd(),
                videoKeyDto.getKeyIdHd(), videoKeyDto.getKeyHd(),
                videoKeyDto.getKeyIdSd(), videoKeyDto.getKeySd(),
                videoKeyDto.getKeyIdNhd(), videoKeyDto.getKeyNhd(),
                videoKeyDto.getKeyIdAudio(), videoKeyDto.getKeyAudio(),
                videoFolder
                );
        return command;
    }
    @Async
    public CompletableFuture<Integer> encryptAndPackaging(String videoId){
        String command;
        int exitVal=-1;
        try{
            command = createPackagingCommand(videoId);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return CompletableFuture.completedFuture(exitVal);
        }
        String[] commandSplit = command.split(" ");
        ProcessBuilder processBuilder = new ProcessBuilder(commandSplit);
        //shaka-packager 는 없는 폴더를 자동으로 생성해주기 때문에 폴더 생성 관련 예외처리가 없어도 됨

        BufferedReader reader;

        try{
            Process process = processBuilder.start();
            String line=null;
            exitVal = process.waitFor();
        } catch(IOException e){
            //실패시 db에 상태 업데이트 하는 기능 추가하면 좋을듯
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            return CompletableFuture.completedFuture(exitVal);
        }
    }
}
