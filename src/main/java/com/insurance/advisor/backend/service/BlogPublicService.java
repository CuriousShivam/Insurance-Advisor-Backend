package com.insurance.advisor.backend.service;

import com.insurance.advisor.backend.entity.Blog;
import com.insurance.advisor.backend.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
public class BlogPublicService {

    @Autowired
    private BlogRepository blogRepository;

    /**
     * Get blogs belonging to a specific category.
     */

    public ResponseEntity<List<Blog>> getBlogsByCategory( UUID categoryId) {
        List<Blog> blogs = blogRepository.findByCategoryIdAndStatus(categoryId, "published");
        return ResponseEntity.ok(blogs);
    }

    /**
     * Search blogs by title or content keywords.
     */

    public ResponseEntity<Blog> getBlogAndIncrementView( UUID id) {
        return blogRepository.findById(id).map(blog -> {
            // 1. Trigger the increment in the DB
            blogRepository.incrementViewCount(id);

            // 2. Update the local object so the response shows the new count
            blog.setViewCount(blog.getViewCount() + 1);

            return ResponseEntity.ok(blog);
        }).orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<List<Blog>> searchBlogs( String query) {
        // We pass the same 'query' string to both title and content search parameters
        List<Blog> results = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatus(
                query, query, "published"
        );
        return ResponseEntity.ok(results);
    }


    public ResponseEntity<List<Blog>> getPublicBlogs() {
        List<Blog> blogs = blogRepository.findPublishedBlogsPrioritized();

        if (blogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blogs);
    }

    public ResponseEntity<Blog> getBlogBySlug( String slug) {
        System.out.println(slug);
        return blogRepository.findBySlugAndStatusIgnoreCase(slug, "published")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
