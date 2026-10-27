package com.insurance.advisor.backend.feature.blog.blog.controller;

import com.insurance.advisor.backend.feature.blog.blog.entity.Blog;
import com.insurance.advisor.backend.feature.blog.blog.service.BlogPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogs")
public class BlogPublicController {

    @Autowired
    private BlogPublicService blogPublicService;

    /**
     * Get blogs belonging to a specific category.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Blog>> getBlogsByCategory(@PathVariable UUID categoryId) {
        return blogPublicService.getBlogsByCategory(categoryId);
    }

    /**
     * Search blogs by title or content keywords.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogAndIncrementView(@PathVariable UUID id) {
        return blogPublicService.getBlogAndIncrementView(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(@RequestParam String query) {
        return blogPublicService.searchBlogs(query);
    }


    @GetMapping
    public ResponseEntity<List<Blog>> getPublicBlogs() {
        return blogPublicService.getPublicBlogs();
    }

    @GetMapping("/post/{slug}")
    public ResponseEntity<Blog> getBlogBySlug(@PathVariable String slug) {

        return blogPublicService.getBlogBySlug(slug);
    }
}