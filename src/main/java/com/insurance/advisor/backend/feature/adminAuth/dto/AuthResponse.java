package com.insurance.advisor.backend.feature.adminAuth.dto;

public record AuthResponse(
        String token,
        String type,
        String expiresInMinutes
) {}