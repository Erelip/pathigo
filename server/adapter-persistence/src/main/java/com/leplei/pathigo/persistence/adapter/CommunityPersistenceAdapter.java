package com.leplei.pathigo.persistence.adapter;

import com.leplei.pathigo.domain.Community;
import com.leplei.pathigo.persistence.entity.CommunityEntity;
import com.leplei.pathigo.persistence.repository.JpaCommunityRepository;
import com.leplei.pathigo.ports.CommunityRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CommunityPersistenceAdapter implements CommunityRepository {

    private final JpaCommunityRepository jpaCommunityRepository;

    public CommunityPersistenceAdapter(JpaCommunityRepository jpaCommunityRepository) {
        this.jpaCommunityRepository = jpaCommunityRepository;
    }

    @Override
    public Optional<Community> findById(UUID id) {
        var jpaCommunity = jpaCommunityRepository.findById(id).orElseThrow();
        Community user = new Community.Builder()
                .id(jpaCommunity.getId())
                .name(jpaCommunity.getName())
                .description(jpaCommunity.getDescription())
                .avatar(jpaCommunity.getAvatar())
                .createdAt(jpaCommunity.getCreatedAt())
                .updatedAt(jpaCommunity.getUpdatedAt())
                .build();
        return Optional.of(user);
    }

    @Override
    public void save(Community user) {
        CommunityEntity entity = new CommunityEntity(user);
        jpaCommunityRepository.save(entity);
    }

    @Override
    public List<Community> findAll() {
        var communities = jpaCommunityRepository.findAll();
        return communities.stream()
                .map(c ->
                        new Community.Builder()
                                .id(c.getId())
                                .name(c.getName())
                                .description(c.getDescription())
                                .avatar(c.getAvatar())
                                .createdAt(c.getCreatedAt())
                                .updatedAt(c.getUpdatedAt())
                                .build())
                .toList();
    }
}
