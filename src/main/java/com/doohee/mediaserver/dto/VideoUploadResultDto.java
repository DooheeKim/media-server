package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.entity.Exposure;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoUploadResultDto {
    @NotNull
    private String title;

    @NotNull
    private Exposure exposure;

    @NotNull
    private String username;

    private String description;

    private String extension;

    @NotNull
    private String videoId;
}
