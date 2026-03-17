package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, UUID> {

    // Custom query to find a category by its slug (useful for Next.js routing)
    Optional<BlogCategory> findBySlug(String slug);

    // Custom query to find only active categories
    java.util.List<BlogCategory> findByIsActiveTrue();

    // 3. Validation: Check if a category name already exists before saving a new one
    boolean existsByName(String name);
}
