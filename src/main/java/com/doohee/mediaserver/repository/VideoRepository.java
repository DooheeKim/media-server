package com.doohee.mediaserver.repository;

import com.doohee.mediaserver.entity.Exposure;
import com.doohee.mediaserver.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findByStatusOrderByUploadedDate(Exposure exposure);
}
