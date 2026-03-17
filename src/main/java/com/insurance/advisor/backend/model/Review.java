package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Entity representing the reviews table for customer feedback.
 * Maps to Table Definition 6.2.3 (Reviews) in the Project Proposal.
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // Unique identifier for each review [cite: 351, 353]

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName; // Name of reviewer [cite: 351, 356]

    @Column(name = "customer_email", nullable = false, length = 100)
    private String customerEmail; // Customer email address [cite: 351, 356]

    @Column(nullable = false, length = 20)
    private String phone; // Customer contact number [cite: 342]

    @Column(name = "service_type", nullable = false, length = 50)
    private String serviceType; // Type of insurance service reviewed [cite: 351, 356]

    @Column(nullable = false)
    private Integer rating; // Customer rating from 1 to 5 stars [cite: 351, 356]

    @Column(name = "review_title", length = 200)
    private String reviewTitle; // Optional title for the review [cite: 351, 356]

    @Column(name = "review_text", nullable = false, columnDefinition = "TEXT")
    private String reviewText; // Detailed customer feedback [cite: 351, 356]

    @Column(name = "consultation_date")
    private LocalDate consultationDate; // Date when consultation was received [cite: 351, 356]

    @Column(name = "submission_date")
    private LocalDateTime submissionDate = LocalDateTime.now(); // Review submission timestamp [cite: 351, 356]

    @Column(name = "approval_status", length = 20)
    private String approvalStatus = "pending"; // Review verification status (pending/approved/rejected) [cite: 351, 356]

    @Column(name = "admin_comments", columnDefinition = "TEXT")
    private String adminComments; // Admin notes during verification [cite: 351, 356]

    @Column(name = "approved_by")
    private UUID approvedBy; // Reference to admin who approved review [cite: 351, 356]

    @Column(name = "approved_at")
    private LocalDateTime approvedAt; // Review approval timestamp [cite: 351, 356]

    @Column(name = "is_featured")
    private Boolean isFeatured = false; // Flag to feature review on homepage [cite: 351, 356]

    @Column(name = "ip_address", length = 45)
    private String ipAddress; // Customer IP for analytics [cite: 351, 356]

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent; // Browser and device information [cite: 351, 356]

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Record creation timestamp [cite: 351, 356]

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Last modification timestamp [cite: 351, 356]
}