package com.leplei.pathigo.ports;

import com.leplei.pathigo.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    void save(User user);

    List<User> findAll();

}
