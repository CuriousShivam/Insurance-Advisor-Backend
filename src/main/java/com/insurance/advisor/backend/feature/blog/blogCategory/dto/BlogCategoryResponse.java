package com.insurance.advisor.backend.feature.blog.blogCategory.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record BlogCategoryResponse(
        @NotBlank
        UUID id,

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String slug,

        @NotBlank
        boolean isActive
) {
}
