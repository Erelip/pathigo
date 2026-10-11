package com.leplei.pathigo.security;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.models.AuthTokens;
import com.leplei.pathigo.ports.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderAdapter implements TokenProvider {

    private final JwtService jwtService;
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    public JwtTokenProviderAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public AuthTokens generateToken(User user) {
        return jwtService.generateToken(user.getEmail());
    }

    @Override
    public AuthTokens refreshToken(String token) {
        if (!jwtService.isTokenValid(token, REFRESH_TOKEN_COOKIE)) {
            return null;
        }

        String username = jwtService.extractUsername(token);

        return jwtService.generateToken(username);
    }
}