package com.doohee.mediaserver.repository;

import com.doohee.mediaserver.entity.UserVideoId;
import com.doohee.mediaserver.entity.UserVideoRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVideoRelationRepository extends JpaRepository<UserVideoRelation, UserVideoId> {
}
