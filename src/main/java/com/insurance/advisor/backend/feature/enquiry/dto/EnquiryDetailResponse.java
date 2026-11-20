package com.insurance.advisor.backend.feature.enquiry.dto;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnquiryDetailResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        InsuranceType insuranceType,
        String requirements,
        LocalDateTime preferredContactTime,
        String sourcePage,
        FollowUpStatus followUpStatus,
        PriorityLevel priorityLevel,
        String adminNotes,
        LocalDateTime contactedAt,
        LocalDateTime convertedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
