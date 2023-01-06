package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.util.CommonUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;



@Data
@Builder
public class VideoAbstractDto {
    String videoId;
    String title;
//    String description;
    //in base64
    String thumbnail;
    String uploader;
    LocalDateTime uploadedDate;

    @QueryProjection
    public VideoAbstractDto(String videoId, String title, String thumbnailExtension, String uploader, LocalDateTime uploadedDate){
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = CommonUtil.thumbnailInBase64(videoId, thumbnailExtension);
        this.uploader = uploader;
        this.uploadedDate = uploadedDate;
    }
}
