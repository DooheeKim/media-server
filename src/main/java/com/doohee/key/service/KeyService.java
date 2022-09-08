package com.doohee.key.service;


import com.doohee.key.dto.VideoKeyDto;
import com.doohee.key.entity.VideoKey;
import com.doohee.key.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KeyService {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    @Autowired
    private KeyRepository keyRepository;

    public String createNBytesHex(int n){
        //n바이트의 16진수 String을 반환합니다.
        byte[] randomBytes = new byte[n];
        new Random().nextBytes(randomBytes); //n Bytes의 랜덤 숫자 생성

        char[] hexChars = new char[n * 2];
        for (int j=0; j<n;j++){
            int v = randomBytes[j] & 0xFF;

            hexChars[j*2] = HEX_ARRAY[v >>> 4];
            hexChars[j*2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void saveKeys(VideoKeyDto videoKeyDto) throws IllegalArgumentException{
        keyRepository.save(videoKeyDto.toEntity());
    }

    public VideoKeyDto createKeys(String videoId) throws IllegalArgumentException{
        //비디오 암호화에 필요한 키들 전부 생성 및 저장, 반환 (default=16bytes)
        final int byteLength = 16;
        VideoKeyDto result = createKeys(videoId, byteLength);
        return result;
    }
    public VideoKeyDto createKeys(String videoId, int byteLength) throws IllegalArgumentException{
        //비디오 암호화에 필요한 키들 전부 생성 및 저장, 반환
        VideoKeyDto result = new VideoKeyDto(videoId,
                createNBytesHex(byteLength), createNBytesHex(byteLength), //1080
                createNBytesHex(byteLength), createNBytesHex(byteLength), //720
                createNBytesHex(byteLength), createNBytesHex(byteLength), //480
                createNBytesHex(byteLength), createNBytesHex(byteLength), //360
                createNBytesHex(byteLength), createNBytesHex(byteLength)); //audio

        saveKeys(result);
        return result;
    }

}
