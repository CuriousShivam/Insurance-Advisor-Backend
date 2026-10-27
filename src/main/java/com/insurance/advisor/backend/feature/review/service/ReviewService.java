package com.insurance.advisor.backend.feature.review.service;

import com.insurance.advisor.backend.feature.review.entity.Review;
import com.insurance.advisor.backend.config.enums.ApprovalStatus;
import com.insurance.advisor.backend.feature.review.repo.ReviewRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * PUBLIC: UC-V05 Submit Service Review
     * Anyone can submit a review, but it defaults to 'pending'.
     */
    public ResponseEntity<String> submitReview( Review review, HttpServletRequest request) {
        review.setApprovalStatus(ApprovalStatus.PENDING);
        review.setIsFeatured(false);

        // Capture metadata for analytics as per proposal
        review.setIpAddress(request.getRemoteAddr());
        review.setUserAgent(request.getHeader("User-Agent"));

        reviewRepository.save(review);
        return ResponseEntity.ok("Review submitted! It will appear on the site once verified.");
    }

    /**
     * PUBLIC: UC-V04 View Testimonials
     * Returns only approved reviews for the frontend.
     */
    public ResponseEntity<List<Review>> getApprovedReviews() {
        // Fetches reviews where approval_status = 'approved'
        List<Review> approvedReviews = reviewRepository.findByApprovalStatusIgnoreCase("approved");

        System.out.println(approvedReviews);
        if (approvedReviews.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no reviews found
        }

        return ResponseEntity.ok(approvedReviews); // Returns 200 OK with the list
    }

    /**
     * ADMIN ONLY: Verify/Approve a Review
     */
    public ResponseEntity<String> verifyReview(
             UUID id,
             String status,
             Boolean featured) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setApprovalStatus(ApprovalStatus.valueOf(status)); // approved or rejected
        if (featured != null) review.setIsFeatured(featured);
        review.setApprovedAt(LocalDateTime.now());

        reviewRepository.save(review);
        return ResponseEntity.ok("Review status updated to: " + status);
    }

    public ResponseEntity<List<Review>> getAllReviewsForAdmin() {
        // We use the built-in findAll() from JpaRepository
        // Sorting by createdAt ensures the newest reviews appear at the top
        List<Review> allReviews = reviewRepository.findAll(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt")
        );

        if (allReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allReviews);
    }

    public ResponseEntity<?> updateReviewStatus( UUID id,  Map<String, Object> updates) {
        return reviewRepository.findById(id).map(review -> {
            if (updates.containsKey("approvalStatus")) {
                review.setApprovalStatus(ApprovalStatus.valueOf((String) updates.get("approvalStatus")));
            }
            if (updates.containsKey("isFeatured")) {
                review.setIsFeatured((Boolean) updates.get("isFeatured"));
            }
            reviewRepository.save(review);
            return ResponseEntity.ok(review);
        }).orElse(ResponseEntity.notFound().build());
    }
}
