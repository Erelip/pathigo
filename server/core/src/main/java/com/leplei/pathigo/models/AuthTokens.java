package com.leplei.pathigo.models;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {}