package com.insurance.advisor.backend.entity;

import com.insurance.advisor.backend.entity.enums.ApprovalStatus;
import com.insurance.advisor.backend.entity.enums.ServiceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing the reviews table for customer feedback.
 * Maps to Table Definition 6.2.3 (Reviews) in the Project Proposal.
 */
@Entity
@Table(name = "reviews",
indexes = {
        @Index(name = "idx_reviews_approval_status", columnList = "approval_status")
})
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // Unique identifier for each review

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName; // Name of reviewer

    @Column(name = "customer_email", nullable = false, length = 100)
    private String customerEmail; // Customer email address

    @Column(nullable = false, length = 20)
    private String phone; // Customer contact number

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 50)
    private ServiceType serviceType; // Type of insurance service reviewed

    @Column(nullable = false)
    private Integer rating; // Customer rating from 1 to 5 stars

    @Column(name = "review_title", length = 200)
    private String reviewTitle; // Optional title for the review

    @Column(name = "review_text", nullable = false, columnDefinition = "TEXT")
    private String reviewText; // Detailed customer feedback

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING; // Review verification status (pending/approved/rejected) [cite: 351, 356]

    @Column(name = "admin_comments", columnDefinition = "TEXT")
    private String adminComments; // Admin notes during verification

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "approved_by",
            foreignKey = @ForeignKey(name = "fk_reviews_approved_by_admin")
    )
    private AdminUser approvedBy;       // Linked directly to AdminUser class

    @Column(name = "approved_at")
    private LocalDateTime approvedAt; // Review approval timestamp

    @Column(name = "is_featured")
    private Boolean isFeatured = false; // Flag to feature review on homepage

    @Column(name = "ip_address", length = 45)
    private String ipAddress; // Customer IP for analytics

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent; // Browser and device information

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Record creation timestamp

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Last modification timestamp
    
}