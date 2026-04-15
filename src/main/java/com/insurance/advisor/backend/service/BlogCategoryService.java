package com.insurance.advisor.backend.service;

import com.insurance.advisor.backend.model.BlogCategory;
import com.insurance.advisor.backend.repository.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogCategoryService {

    @Autowired
    private BlogCategoryRepository categoryRepository;

    /**
     * Handles the creation of a new blog category with auditing fields[cite: 373, 377].
     */
    public BlogCategory createCategory(BlogCategory category) {
        // Set auditing: Record the creation timestamp in PostgreSQL [cite: 373]
        category.setCreatedAt(LocalDateTime.now());

        // Default logic: Set category to active if not specified [cite: 373]
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }

        return categoryRepository.save(category);
    }

    /**
     * Fetches all categories to populate the Blog Creation forms[cite: 748].
     */
    public List<BlogCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
}