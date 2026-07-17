package com.leplei.pathigo.persistence.repository;

import com.leplei.pathigo.persistence.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCommunityRepository extends JpaRepository<CommunityEntity, UUID> {
    Optional<CommunityEntity> findByName(String email);
}