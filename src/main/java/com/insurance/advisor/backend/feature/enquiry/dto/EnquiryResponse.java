package com.insurance.advisor.backend.feature.enquiry.dto;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnquiryResponse(
        @NotBlank
        UUID id,

        @NotBlank
        String fullName,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String phone,

        @NotBlank
        InsuranceType insuranceType

        ) {
        public static record EnquiryStatusUpdateRequest(
                @NotNull(message = "Follow-up status is required")
                FollowUpStatus followUpStatus,

                PriorityLevel priorityLevel,

                String adminNotes,
                LocalDateTime convertedAt,
                LocalDateTime contactedAt
        ) {
        }
}
