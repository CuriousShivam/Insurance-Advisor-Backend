package com.insurance.advisor.backend.dto;

public record AuthResponse(
        String token,
        String type,
        String expiresInMinutes
) {}