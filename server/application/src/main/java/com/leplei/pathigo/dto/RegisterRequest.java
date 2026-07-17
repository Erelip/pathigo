package com.leplei.pathigo.dto;

public record RegisterRequest(
        String email,
        String username,
        String password,
        String nickname,
        String phoneNumber
) { }