package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.entity.VideoKey;
import lombok.*;

@Getter
@Setter
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

    public VideoKey toEntity(){
        return new VideoKey(videoId, keyIdFhd, keyFhd, keyIdHd, keyHd, keyIdSd, keySd, keyIdNhd, keyNhd, keyIdAudio, keyAudio);
    }
}
