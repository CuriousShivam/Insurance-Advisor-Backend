package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.entity.Blog;
import com.insurance.advisor.backend.entity.BlogCategory;
import com.insurance.advisor.backend.repository.BlogCategoryRepository;
import com.insurance.advisor.backend.repository.BlogRepository;
import com.insurance.advisor.backend.service.BlogAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/blogs")
public class BlogAdminController {

    @Autowired
    private BlogAdminService blogAdminService;

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        return blogAdminService.createBlog(blog);
    }

    /**
     * ADMIN ONLY: Fetch blog by ID for editing.
     * Does NOT increment view count.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogForEdit(@PathVariable UUID id) {
        return blogAdminService.getBlogForEdit(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable UUID id, @RequestBody Blog blogDetails) {
       return blogAdminService.updateBlog(id,blogDetails);
    }

    /**
     * ADMIN ONLY: Delete a blog post by ID.
     * Follows UC-A04 (Manage Content) from your proposal.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable UUID id) {
        return blogAdminService.deleteBlog(id);
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        // Fetch all blogs from PostgreSQL


        return blogAdminService.getAllBlogs();
    }
}



