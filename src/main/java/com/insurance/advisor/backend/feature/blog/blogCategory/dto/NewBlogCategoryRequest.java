package com.insurance.advisor.backend.feature.blog.blogCategory.dto;

import jakarta.validation.constraints.NotBlank;

public record NewBlogCategoryRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String slug
) {
}
