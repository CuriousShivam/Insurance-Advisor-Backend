package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Entity representing the admin_users table for advisor authentication.
 * Maps to Table Definition 6.2.3 in the Project Proposal.
 */
@Entity
@Table(name = "admin_users")
@Getter
@Setter
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // Unique identifier for admin user [cite: 326, 331]

    @Column(nullable = false, unique = true, length = 50)
    private String username; // Login username for advisor [cite: 329, 331]

    @Column(nullable = false, unique = true, length = 100)
    private String email; // Email for admin communications [cite: 329, 331]

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash; // BCrypt hashed password for security [cite: 329, 331]

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName; // Full name of insurance advisor [cite: 329, 331]

    @Column(length = 20)
    private String phone; // Contact phone number [cite: 329, 331]

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Account creation timestamp [cite: 329, 331]

    @Column(name = "last_login")
    private LocalDateTime lastLogin; // Last successful login timestamp [cite: 329, 331]

    @Column(name = "is_active")
    private Boolean isActive = true; // Account status flag [cite: 329, 334]

    @Column(length = 20)
    private String role = "admin"; // User role for future expansion [cite: 329, 334]
}