package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.Blog;
import com.insurance.advisor.backend.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogs")
public class BlogPublicController {

    @Autowired
    private BlogRepository blogRepository;

    /**
     * Get blogs belonging to a specific category.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Blog>> getBlogsByCategory(@PathVariable UUID categoryId) {
        List<Blog> blogs = blogRepository.findByCategoryIdAndStatus(categoryId, "published");
        return ResponseEntity.ok(blogs);
    }

    /**
     * Search blogs by title or content keywords.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogAndIncrementView(@PathVariable UUID id) {
        return blogRepository.findById(id).map(blog -> {
            // 1. Trigger the increment in the DB
            blogRepository.incrementViewCount(id);

            // 2. Update the local object so the response shows the new count
            blog.setViewCount(blog.getViewCount() + 1);

            return ResponseEntity.ok(blog);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(@RequestParam String query) {
        // We pass the same 'query' string to both title and content search parameters
        List<Blog> results = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatus(
                query, query, "published"
        );
        return ResponseEntity.ok(results);
    }


    @GetMapping
    public ResponseEntity<List<Blog>> getPublicBlogs() {
        List<Blog> blogs = blogRepository.findPublishedBlogsPrioritized();

        if (blogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/post/{slug}")
    public ResponseEntity<Blog> getBlogBySlug(@PathVariable String slug) {
        System.out.println(slug);
        return blogRepository.findBySlugAndStatusIgnoreCase(slug, "published")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}