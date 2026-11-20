package com.insurance.advisor.backend.feature.blog.blogCategory.repo;

import com.insurance.advisor.backend.feature.blog.blogCategory.dto.BlogCategoryResponse;
import com.insurance.advisor.backend.feature.blog.blogCategory.entity.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, UUID> {

    // Custom query to find a category by its slug (useful for Next.js routing)
    Optional<BlogCategory> findBySlug(String slug);

    // Custom query to find only active categories
    java.util.List<BlogCategory> findByIsActiveTrue();

    // 3. Validation: Check if a category name already exists before saving a new one
    boolean existsByName(String name);

    Page<BlogCategoryResponse> findBy(Pageable pageable);
}
