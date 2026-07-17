package com.leplei.pathigo.persistence.adapter;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.persistence.entity.UserEntity;
import com.leplei.pathigo.persistence.repository.JpaUserRepository;
import com.leplei.pathigo.ports.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserPersistenceAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        var jpaUser = jpaUserRepository.findById(id).orElseThrow();
        User user = new User.Builder()
                .id(jpaUser.getId())
                .email(jpaUser.getEmail())
                .username(jpaUser.getUsername())
                .password(jpaUser.getPassword())
                .nickname(jpaUser.getNickname())
                .phoneNumber(jpaUser.getPhoneNumber())
                .build();
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity(user);
        jpaUserRepository.save(entity);
    }

    @Override
    public List<User> findAll() {
        var users = jpaUserRepository.findAll();
        return users.stream()
                .map(UserEntity::toDomain)
                .toList();
    }
}
