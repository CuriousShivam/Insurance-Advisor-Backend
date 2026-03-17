package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    // For UC-V04: View approved testimonials
    List<Review> findByApprovalStatusIgnoreCase(String status);

    // For Homepage: Fetch featured reviews
    List<Review> findByIsFeaturedTrueAndApprovalStatus(String status);
}