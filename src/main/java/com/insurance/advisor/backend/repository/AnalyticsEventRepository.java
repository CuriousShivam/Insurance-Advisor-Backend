package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, UUID> {

    // Find all events for a specific button/action
    List<AnalyticsEvent> findByEventName(String eventName);

    // Count events of a certain type
    long countByEventType(String eventType);
}