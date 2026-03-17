package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Entity representing the enquiries table for lead collection.
 * Maps to Table Definition 6.2.4 in the Project Proposal. [cite: 335, 340]
 */
@Entity
@Table(name = "enquiries")
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

    @Column(name = "insurance_type", nullable = false, length = 50)
    private String insuranceType; // Type of insurance (Health, Life, etc.) [cite: 342]

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requirements; // Detailed customer requirements [cite: 342]

    @Column(name = "preferred_contact_time", length = 50)
    private String preferredContactTime; // Customer's preferred contact time [cite: 342]

    @Column(name = "source_page", length = 100)
    private String sourcePage; // Website page that generated the enquiry [cite: 345]

    @Column(name = "follow_up_status", length = 20)
    private String followUpStatus = "pending"; // Current status (pending, contacted, converted) [cite: 340, 345]

    @Column(name = "priority_level", length = 10)
    private String priorityLevel = "normal"; // Business priority of enquiry [cite: 340, 345]

    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes; // Internal notes by advisor [cite: 345]

    @Column(name = "contacted_at")
    private LocalDateTime contactedAt; // Timestamp when customer was contacted [cite: 345]

    @Column(name = "converted_at")
    private LocalDateTime convertedAt; // Timestamp when enquiry converted [cite: 345]

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Enquiry submission timestamp [cite: 340, 345]

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Last modification timestamp [cite: 340, 345]
}