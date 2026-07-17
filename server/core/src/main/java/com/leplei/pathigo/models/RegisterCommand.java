package com.leplei.pathigo.models;

public record RegisterCommand(
        String email,
        String username,
        String password,
        String nickname,
        String phoneNumber
) { }