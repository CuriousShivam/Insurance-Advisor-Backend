package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.BlogCategory;
import com.insurance.advisor.backend.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BlogCategory> createCategory(@RequestBody BlogCategory category) {
        // Delegate processing logic to the Service layer [cite: 310-313]
        BlogCategory savedCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    /**
     * Public/Admin: List all categories for navigation and post organization[cite: 743].
     */
    @GetMapping
    public ResponseEntity<List<BlogCategory>> getAllCategories() {
        List<BlogCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}