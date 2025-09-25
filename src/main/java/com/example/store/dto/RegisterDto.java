package com.example.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @Email @NotBlank String email,
        @NotBlank String fullName,
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {}

