package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {

    // Find logs by recipient for audit purposes
    List<NotificationLog> findByRecipientEmailOrderByCreatedAtDesc(String email);

    // Find failed notifications to retry them
    List<NotificationLog> findByDeliveryStatus(String status);
}