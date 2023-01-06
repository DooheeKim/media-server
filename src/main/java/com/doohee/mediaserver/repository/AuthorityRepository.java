package com.doohee.mediaserver.repository;

import com.doohee.mediaserver.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
