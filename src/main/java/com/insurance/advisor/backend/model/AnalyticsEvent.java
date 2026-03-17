package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "analytics_events")
@Getter @Setter
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType; // e.g., "CLICK", "PAGE_VIEW", "FORM_SUBMIT"

    @Column(name = "event_name", nullable = false, length = 100)
    private String eventName; // e.g., "GET_QUOTE_BUTTON", "HOME_PAGE_VIEW"

    @Column(name = "page_url")
    private String pageUrl; // Where the event happened

    @Column(columnDefinition = "TEXT")
    private String eventData; // JSON or string for extra info (e.g., "plan_type: health")

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "user_id")
    private UUID userId; // Optional: Null for guests, filled for logged-in admins

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}