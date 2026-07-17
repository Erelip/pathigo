package com.leplei.pathigo.security;

import com.leplei.pathigo.domain.User;
import com.leplei.pathigo.ports.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderAdapter implements TokenProvider {

    private final JwtService jwtService;

    public JwtTokenProviderAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(User user) {
        return jwtService.generateToken(user.getEmail());
    }
}