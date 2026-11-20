package com.insurance.advisor.backend.feature.blog.blogCategory.controller;

import com.insurance.advisor.backend.feature.blog.blogCategory.dto.NewBlogCategoryRequest;
import com.insurance.advisor.backend.feature.blog.blogCategory.dto.BlogCategoryResponse;
import com.insurance.advisor.backend.feature.blog.blogCategory.service.BlogCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryService categoryService;

    /**
     * ADMIN ONLY: Create a new category for insurance blogs[cite: 66, 286].
     */
    @PostMapping
    public ResponseEntity<BlogCategoryResponse> createCategory(@Valid @RequestBody NewBlogCategoryRequest category) {
        // Delegate processing logic to the Service layer [cite: 310-313]
        BlogCategoryResponse savedCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    /**
     * Public/Admin: List all categories for navigation and post organization[cite: 743].
     */
    @GetMapping
    public ResponseEntity<Page<BlogCategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
            ) {
        Page<BlogCategoryResponse> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(categories);
    }
}