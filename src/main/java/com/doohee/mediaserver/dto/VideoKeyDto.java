package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.entity.Video;
import com.doohee.mediaserver.entity.VideoKey;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VideoKeyDto {
    private String videoId;
    //1080p
    private String keyIdFhd;
    private String keyFhd;

    //720p
    private String keyIdHd;
    private String keyHd;

    //480p
    private String keyIdSd;
    private String keySd;

    //360p
    private String keyIdNhd;
    private String keyNhd;

    //audio
    private String keyIdAudio;
    private String keyAudio;

    public VideoKeyDto(VideoKey videoKey){
        this.videoId = videoKey.getVideoId();
        this.keyIdFhd = videoKey.getKeyIdFhd();
        this.keyFhd = videoKey.getKeyFhd();
        this.keyIdHd = videoKey.getKeyIdHd();
        this.keyHd = videoKey.getKeyHd();
        this.keyIdSd = videoKey.getKeyIdSd();
        this.keySd = videoKey.getKeySd();
        this.keyIdNhd = videoKey.getKeyIdNhd();
        this.keyNhd = videoKey.getKeyNhd();
        this.keyIdAudio = videoKey.getKeyIdAudio();
        this.keyAudio = videoKey.getKeyAudio();
    }
    public VideoKey toEntity(){
        return VideoKey.builder()
                .videoId(this.videoId)
                .keyIdFhd(this.keyIdFhd)
                .keyFhd(this.keyFhd)
                .keyIdHd(this.keyIdHd)
                .keyHd(this.keyHd)
                .keyIdSd(this.keyIdSd)
                .keySd(this.keySd)
                .keyIdNhd(this.keyIdNhd)
                .keyNhd(this.keyNhd)
                .keyIdAudio(this.keyIdAudio)
                .keyAudio(this.keyAudio)
                .build();
    }
}
