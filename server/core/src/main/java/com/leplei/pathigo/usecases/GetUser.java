package com.leplei.pathigo.usecases;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.ports.UserRepository;

import java.util.Optional;

public class GetUser {

    private final UserRepository userRepository;

    public GetUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void save(User user) {
        this.userRepository.save(user);
    }
}
