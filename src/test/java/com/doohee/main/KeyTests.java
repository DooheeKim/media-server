package com.doohee.main;

import com.doohee.key.dto.VideoKeyDto;
import com.doohee.key.repository.KeyRepository;
import com.doohee.key.service.KeyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class KeyTests {
    @Autowired
    KeyService keyService;

    @Autowired
    KeyRepository keyRepository;

    @Test
    public void lengthOfBytes(){
        String bytes = keyService.createNBytesHex(16);
        assertThat(bytes.length()).isEqualTo(32);
        assertThat(bytes).isNotEqualTo(keyService.createNBytesHex(16)); //새로 만들때마다 달라지는지 테스트
    }
    @Test
    public void createKeysTest(){
        VideoKeyDto videoKeyDto = keyService.createKeys("test_video_id");
        //16bytes 키가 제대로 만들어졌는지 테스트
        int byteLength = 16;
        int stringLength = byteLength*2;
        assertThat(videoKeyDto.getKeyIdFhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyFhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdHd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyHd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdSd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeySd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdNhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyNhd().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyIdAudio().length()).isEqualTo(stringLength);
        assertThat(videoKeyDto.getKeyAudio().length()).isEqualTo(stringLength);

        assertThat(videoKeyDto).isNotEqualTo(keyService.createKeys("test_video_id"));
        //새로 만들때마다 랜덤값으로 제대로 만들어지는 지 테스트
    }

    @Test
    public void saveKeyTest(){
        keyRepository.deleteAll();
        assertThat(keyRepository.count()).isEqualTo(0);
        keyService.saveKeys(keyService.createKeys("test_video_id1"));
        assertThat(keyRepository.count()).isEqualTo(1);
        keyService.saveKeys(keyService.createKeys("test_video_id2"));
        assertThat(keyRepository.count()).isEqualTo(2);
    }
}
