package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.BlogCategory;
import com.insurance.advisor.backend.repository.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<BlogCategory> createCategory(@RequestBody BlogCategory category) {
        // Set auditing field
        category.setCreatedAt(LocalDateTime.now());
        if (category.getIsActive() == null) category.setIsActive(true);

        BlogCategory savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    // 2. List all Categories (Useful for the Blog Creation form)
    @GetMapping
    public List<BlogCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
}