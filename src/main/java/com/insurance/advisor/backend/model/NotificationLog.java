package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Entity representing the notification_log table.
 * Tracks system-generated notifications and their delivery status.
 */
@Entity
@Table(name = "notification_logs")
@Getter
@Setter
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "recipient_email", nullable = false, length = 100)
    private String recipientEmail; // Who the notification was for

    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType; // e.g., "ENQUIRY_ALERT", "REVIEW_APPROVED", "AUTH_ALERT"

    @Column(nullable = false, length = 200)
    private String subject; // Email subject line

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message; // The actual content sent

    @Column(name = "delivery_status", length = 20)
    private String deliveryStatus = "pending"; // pending, sent, failed

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage; // Log details if delivery failed

    @Column(name = "sent_at")
    private LocalDateTime sentAt; // Timestamp when successfully sent

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}