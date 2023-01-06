package com.doohee.mediaserver.repository;
import com.doohee.mediaserver.entity.VideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
public interface KeyRepository extends JpaRepository<VideoKey, String> {
}
