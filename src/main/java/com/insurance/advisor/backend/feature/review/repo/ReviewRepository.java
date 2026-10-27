package com.insurance.advisor.backend.feature.review.repo;

import com.insurance.advisor.backend.feature.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    // For UC-V04: View approved testimonials
    List<Review> findByApprovalStatusIgnoreCase(String status);

    // For Homepage: Fetch featured reviews
    List<Review> findByIsFeaturedTrueAndApprovalStatus(String status);
}