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
public class VideoUploadDto {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private Exposure exposure;
}
