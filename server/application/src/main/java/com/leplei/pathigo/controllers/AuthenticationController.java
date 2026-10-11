package com.leplei.pathigo.controllers;

import com.leplei.pathigo.models.AuthTokens;
import com.leplei.pathigo.dto.LoginRequest;
import com.leplei.pathigo.dto.RegisterRequest;
import com.leplei.pathigo.models.RegisterCommand;
import com.leplei.pathigo.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";
    private static final Duration ACCESS_TOKEN_MAX_AGE = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_MAX_AGE = Duration.ofDays(7);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@Valid @RequestBody LoginRequest dto) {
        AuthTokens token = authenticationService.login(
                dto.email(),
                dto.password()
        );

        return tokenResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest dto) {
        AuthTokens token = authenticationService.register(
                new RegisterCommand(
                        dto.email(),
                        dto.username(),
                        dto.password(),
                        dto.nickname(),
                        dto.phoneNumber(),
                        null,
                        null
                )
        );

        return tokenResponse(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(
                    name = REFRESH_TOKEN_COOKIE,
                    required = false
            ) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return clearCookies();
        }

        try {
            AuthTokens tokens = authenticationService.refresh(refreshToken);

            return tokenResponse(tokens);
        } catch (org.springframework.security.core.AuthenticationException
                 | IllegalArgumentException ex) {
            return clearCookies();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return clearCookies();
    }

    private ResponseEntity<Void> clearCookies() {
        ResponseCookie accessCookie = createCookie(
                ACCESS_TOKEN_COOKIE, "", Duration.ZERO
        );

        ResponseCookie refreshCookie = createCookie(
                REFRESH_TOKEN_COOKIE, "", Duration.ZERO
        );

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    private ResponseEntity<Void> tokenResponse(AuthTokens tokens) {
        ResponseCookie accessCookie = createCookie(
                ACCESS_TOKEN_COOKIE,
                tokens.accessToken(),
                ACCESS_TOKEN_MAX_AGE
        );

        ResponseCookie refreshCookie = createCookie(
                REFRESH_TOKEN_COOKIE,
                tokens.refreshToken(),
                REFRESH_TOKEN_MAX_AGE
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    private ResponseCookie createCookie(
            String name,
            String value,
            Duration maxAge
    ) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("none")
                .build();
    }
}