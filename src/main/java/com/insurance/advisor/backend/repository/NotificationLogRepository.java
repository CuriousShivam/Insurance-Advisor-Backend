package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {

    // Find logs by recipient for audit purposes
    List<NotificationLog> findByRecipientEmailOrderByCreatedAtDesc(String email);

    // Find failed notifications to retry them
    List<NotificationLog> findByDeliveryStatus(String status);
}