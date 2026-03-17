package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.Blog;
import com.insurance.advisor.backend.model.BlogCategory;
import com.insurance.advisor.backend.repository.BlogRepository;
import com.insurance.advisor.backend.repository.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/blogs")
public class BlogAdminController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository categoryRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        // 1. Validate Category Existence
        if (blog.getCategory() == null || blog.getCategory().getId() == null) {
            return ResponseEntity.badRequest().body("Error: Category ID is required.");
        }

        UUID categoryId = blog.getCategory().getId();
        BlogCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        // 2. Prevent Duplicate Slugs
        if (blogRepository.existsBySlug(blog.getSlug())) {
            return ResponseEntity.badRequest().body("Error: Slug '" + blog.getSlug() + "' already exists.");
        }

        // 3. Set Auditing & Default Fields
        blog.setCategory(category);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        if (blog.getViewCount() == null) blog.setViewCount(0);
        if (blog.getStatus() == null) blog.setStatus("draft");

        // 4. Save and Return
        Blog savedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(savedBlog);
    }

    /**
     * ADMIN ONLY: Fetch blog by ID for editing.
     * Does NOT increment view count.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogForEdit(@PathVariable UUID id) {
        return blogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable UUID id, @RequestBody Blog blogDetails) {
        return blogRepository.findById(id).map(blog -> {
            // 1. Basic Info
            blog.setTitle(blogDetails.getTitle());
            blog.setContent(blogDetails.getContent());
            blog.setExcerpt(blogDetails.getExcerpt());

            // 2. SEO & Metadata
            blog.setMetaTitle(blogDetails.getMetaTitle());
            blog.setMetaDescription(blogDetails.getMetaDescription());
            blog.setMetaKeywords(blogDetails.getMetaKeywords());
            blog.setFeaturedImage(blogDetails.getFeaturedImage());

            // 3. Status & Featured
            blog.setStatus(blogDetails.getStatus());
            blog.setIsFeatured(blogDetails.getIsFeatured());

            // 4. Handle Category Change (if provided)
            if (blogDetails.getCategory() != null && blogDetails.getCategory().getId() != null) {
                blog.setCategory(blogDetails.getCategory());
            }

            // 5. Update Timestamp (Handled by @PreUpdate in Entity, but can be manual)
            blog.setUpdatedAt(LocalDateTime.now());

            Blog updatedBlog = blogRepository.save(blog);
            return ResponseEntity.ok(updatedBlog);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * ADMIN ONLY: Delete a blog post by ID.
     * Follows UC-A04 (Manage Content) from your proposal.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable UUID id) {
        return blogRepository.findById(id).map(blog -> {
            blogRepository.delete(blog);
            return ResponseEntity.ok("Blog deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }
}



