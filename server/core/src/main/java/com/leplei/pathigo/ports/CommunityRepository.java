package com.leplei.pathigo.ports;

import com.leplei.pathigo.domain.Community;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommunityRepository {

    Optional<Community> findById(UUID id);

    void save(Community user);

    List<Community> findAll();

}
