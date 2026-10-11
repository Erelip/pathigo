package com.leplei.pathigo.services;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.models.AuthTokens;
import com.leplei.pathigo.models.RegisterCommand;
import com.leplei.pathigo.ports.PasswordHasher;
import com.leplei.pathigo.ports.TokenProvider;
import com.leplei.pathigo.exceptions.EmailAlreadyExistsException;
import com.leplei.pathigo.exceptions.InvalidCredentialsException;
import com.leplei.pathigo.usecases.GetUser;

import java.util.Optional;

public class AuthenticationService {

    private final GetUser getUser;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;
    private final String PROVIDER = "GOOGLE";

    public AuthenticationService(GetUser getUser, PasswordHasher passwordHasher, TokenProvider tokenProvider) {
        this.getUser = getUser;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
    }

    public AuthTokens login(String email, String rawPassword) {
        User user = getUser.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.generateToken(user);
    }

    public AuthTokens register(RegisterCommand command) {
        if (getUser.findByEmail(command.email()).isPresent()) {
            throw new EmailAlreadyExistsException(command.email());
        }

        User user = User.builder()
                .email(command.email())
                .username(command.username())
                .password(passwordHasher.hash(command.password()))
                .nickname(command.nickname())
                .phoneNumber(command.phoneNumber())
                .build();

        getUser.save(user);

        return tokenProvider.generateToken(user);
    }

    public AuthTokens loginOrRegisterGoogle(String googleSubject, String email, String fullName) {
        Optional<User> user = getUser.findByEmail(email);

        if (user.isPresent()) {
            return tokenProvider.generateToken(user.get());
        }

        User newUser = User.builder()
                .email(email)
                .nickname(fullName)
                .provider(PROVIDER)
                .providerId(googleSubject)
                .build();
        getUser.save(newUser);

        return  tokenProvider.generateToken(newUser);
    }

    public AuthTokens refresh(String token) {
        AuthTokens tokens = tokenProvider.refreshToken(token);
        if (tokens == null) {
            throw new InvalidCredentialsException();
        }

        return tokens;
    }
}