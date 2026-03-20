package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    // Find blogs by category for your dashboard filters
    List<Blog> findByCategoryId(UUID categoryId);

    // Check if a slug already exists to prevent duplicate URLs
    boolean existsBySlug(String slug);

    // Filter by Category ID and ensure only published blogs are shown
    List<Blog> findByCategoryIdAndStatus(UUID categoryId, String status);

    // Search by Title or Content (Case-Insensitive)
    List<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatus(
            String title, String content, String status
    );

    @Transactional
    @Modifying
    @Query("UPDATE Blog b SET b.viewCount = b.viewCount + 1 WHERE b.id = :id")
    void incrementViewCount(UUID id);

    @Query("SELECT b FROM Blog b WHERE LOWER(b.status) = 'published' " +
            "ORDER BY b.isFeatured DESC, b.viewCount DESC")
    List<Blog> findPublishedBlogsPrioritized();

    Optional<Blog> findBySlug(String slug);

    // If you only want to allow viewing of published blogs by slug:
    Optional<Blog> findBySlugAndStatusIgnoreCase(String slug, String status);
}