package com.leplei.pathigo.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @NotBlank @Size(min = 3, max = 30)
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "must contain only letters, digits, '_', '.' or '-'")
        String username,

        @NotBlank @Size(min = 8, max = 72)
        String password,

        @NotBlank @Size(max = 50)
        String nickname,

        @NotBlank @Size(max = 50)
        // @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "must be a valid phone number")
        String phoneNumber
) { }