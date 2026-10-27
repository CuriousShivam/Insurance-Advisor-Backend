package com.insurance.advisor.backend.feature.adminAuth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entity representing the admin_users table for advisor authentication.
 * Maps to Table Definition 6.2.3 in the Project Proposal.
 */
@Entity
@Table(name = "admin_users",
indexes = {
        @Index(name="idx_admin_users_email", columnList = "email")
})
@Getter
@Setter
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Account creation timestamp

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin; // Last successful login timestamp

    @Column(name = "is_active")
    private Boolean isActive = true; // Account status flag

    @Column(length = 20)
    private String role = "admin"; // User role for future expansion

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