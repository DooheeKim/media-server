package com.doohee.key.repository;
import com.doohee.key.entity.VideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
public interface KeyRepository extends JpaRepository<VideoKey, String> {
}
