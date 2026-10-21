package com.insurance.advisor.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Account creation timestamp

    @Column(name = "last_login")
    private LocalDateTime lastLogin; // Last successful login timestamp

    @Column(name = "is_active")
    private Boolean isActive = true; // Account status flag

    @Column(length = 20)
    private String role = "admin"; // User role for future expansion
}