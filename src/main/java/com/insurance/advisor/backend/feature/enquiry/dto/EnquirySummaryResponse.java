package com.insurance.advisor.backend.feature.enquiry.dto;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnquirySummaryResponse(
        UUID id,
        String fullName,
        String phone,
        String email,
        InsuranceType insuranceType,
        FollowUpStatus followUpStatus,
        PriorityLevel priorityLevel,
        LocalDateTime createdAt
) {
}
