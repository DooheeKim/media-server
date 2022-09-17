package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.entity.Exposure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class VideoUploadData {
    private MultipartFile file;
    private Exposure exposure;
    private String title;
    private String description;
}
