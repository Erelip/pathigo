package com.leplei.pathigo.services;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.models.RegisterCommand;
import com.leplei.pathigo.ports.PasswordHasher;
import com.leplei.pathigo.ports.TokenProvider;
import com.leplei.pathigo.ports.UserRepository;
import com.leplei.pathigo.exceptions.EmailAlreadyExistsException;
import com.leplei.pathigo.exceptions.InvalidCredentialsException;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    public AuthenticationService(UserRepository userRepository, PasswordHasher passwordHasher, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.generateToken(user);
    }

    public String register(RegisterCommand command) {
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new EmailAlreadyExistsException(command.email());
        }

        User user = User.builder()
                .email(command.email())
                .username(command.username())
                .password(passwordHasher.hash(command.password()))
                .nickname(command.nickname())
                .phoneNumber(command.phoneNumber())
                .build();
        userRepository.save(user);

        return tokenProvider.generateToken(user);
    }
}