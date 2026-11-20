package com.insurance.advisor.backend.feature.adminAuth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginCredentials (
        @NotNull
        @Email(message = "Valid Email is required")
        String email,

        @NotNull(message = "Password is required")
        String password
){}