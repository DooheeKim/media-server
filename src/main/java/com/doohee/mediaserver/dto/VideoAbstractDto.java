package com.doohee.mediaserver.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class VideoAbstractDto {
    String videoId;
    String title;
    String description;
    //in base64
    String thumbnail;
    String uploader;
}
