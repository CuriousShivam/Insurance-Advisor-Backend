package com.insurance.advisor.backend.feature.review.controller;

import com.insurance.advisor.backend.feature.review.entity.Review;
import com.insurance.advisor.backend.feature.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * PUBLIC: UC-V05 Submit Service Review
     * Anyone can submit a review, but it defaults to 'pending'.
     */
    @PostMapping("/reviews/submit")
    public ResponseEntity<String> submitReview(@RequestBody Review review, HttpServletRequest request) {
        return reviewService.submitReview(review, request);
    }

    /**
     * PUBLIC: UC-V04 View Testimonials
     * Returns only approved reviews for the frontend.
     */
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getApprovedReviews() {
        // Fetches reviews where approval_status = 'approved'


        return reviewService.getApprovedReviews(); // Returns 200 OK with the list
    }

    /**
     * ADMIN ONLY: Verify/Approve a Review
     */
    @PutMapping("/admin/reviews/verify/{id}")
    public ResponseEntity<String> verifyReview(
            @PathVariable UUID id,
            @RequestParam String status,
            @RequestParam(required = false) Boolean featured) {

        return reviewService.verifyReview(id, status, Objects.requireNonNullElse(featured, false));

    }

    @GetMapping("/admin/reviews/all")
    public ResponseEntity<List<Review>> getAllReviewsForAdmin() {
        return reviewService.getAllReviewsForAdmin();
    }

    @PatchMapping("/admin/reviews/{id}")
    public ResponseEntity<?> updateReviewStatus(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        return reviewService.updateReviewStatus(id, updates);
    }

}