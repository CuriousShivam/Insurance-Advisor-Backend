package com.insurance.advisor.backend.feature.enquiry.entity;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entity representing the enquiries table for lead collection.
 * Maps to Table Definition 6.2.4 in the Project Proposal. [cite: 335, 340]
 */
@Entity
@Table(name = "enquiries",
indexes = {
@Index(name = "idx_enquiry_status", columnList = "follow_up_status"),
@Index(name = "idx_enquiry_created_at", columnList = "created_at DESC"),
@Index(name = "idx_enquiry_contact_lookup", columnList = "email, phone")
})
@Getter
@Setter
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // Unique identifier for each enquiry [cite: 342]

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName; // Customer's complete name [cite: 342]

    @Column(nullable = false, length = 100)
    private String email; // Customer email for follow-up [cite: 342]

    @Column(nullable = false, length = 20)
    private String phone; // Customer contact number [cite: 342]

    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_type", nullable = false, length = 50)
    private InsuranceType insuranceType; // Type of insurance (Health, Life, etc.) [cite: 342]

    @Column( columnDefinition = "TEXT")
    private String requirements; // Detailed customer requirements [cite: 342]

    @Column(name = "preferred_contact_time", length = 50)
    private String preferredContactTime; // Customer's preferred contact time [cite: 342]

    @Column(name = "source_page", length = 100)
    private String sourcePage; // Website page that generated the enquiry [cite: 345]

    @Enumerated(EnumType.STRING)
    @Column(name = "follow_up_status", length = 20)
    private FollowUpStatus followUpStatus = FollowUpStatus.NEW; // Current status (pending, contacted, converted) [cite: 340, 345]

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level", length = 10)
    private PriorityLevel priorityLevel = PriorityLevel.MEDIUM; // Business priority of enquiry [cite: 340, 345]

    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes; // Internal notes by advisor [cite: 345]

    @Column(name = "contacted_at")
    private LocalDateTime contactedAt; // Timestamp when customer was contacted [cite: 345]

    @Column(name = "converted_at")
    private LocalDateTime convertedAt; // Timestamp when enquiry converted [cite: 345]

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Enquiry submission timestamp [cite: 340, 345]

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Last modification timestamp [cite: 340, 345]

    // Standard regex pattern for email format
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    @PrePersist
    @PreUpdate
    public void validateEmail() {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format before saving: " + email);
        }
    }
}