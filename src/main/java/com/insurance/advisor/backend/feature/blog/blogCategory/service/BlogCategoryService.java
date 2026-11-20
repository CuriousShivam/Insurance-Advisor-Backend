package com.insurance.advisor.backend.feature.blog.blogCategory.service;

import com.insurance.advisor.backend.feature.blog.blogCategory.dto.NewBlogCategoryRequest;
import com.insurance.advisor.backend.feature.blog.blogCategory.dto.BlogCategoryResponse;
import com.insurance.advisor.backend.feature.blog.blogCategory.entity.BlogCategory;
import com.insurance.advisor.backend.feature.blog.blogCategory.repo.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryService {

    @Autowired
    private BlogCategoryRepository categoryRepository;

    /**
     * Handles the creation of a new blog category with auditing fields[cite: 373, 377].
     */
    public BlogCategoryResponse createCategory(NewBlogCategoryRequest category) {
        BlogCategory bc = new BlogCategory();
        bc.setName(category.name());
        bc.setSlug(category.slug());
        bc.setDescription(category.description());
        BlogCategory newBlogCategory = categoryRepository.save(bc);
        return new BlogCategoryResponse(
                newBlogCategory.getId(),
                newBlogCategory.getName(),
                newBlogCategory.getDescription(),
                newBlogCategory.getSlug(),
                newBlogCategory.getIsActive()
        );
    }

    /**
     * Fetches all categories to populate the Blog Creation forms[cite: 748].
     */
    public Page<BlogCategoryResponse> getAllCategories(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findBy(pageable);
    }
}