package com.insurance.advisor.backend.feature.enquiry.dto;

import com.insurance.advisor.backend.config.enums.InsuranceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record NewEnquiryFields(
        @NotBlank(message="Invalid name")
        String fullName,

        @NotBlank
        @Email(message = "Invalid email format")
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?:\\+91|0)?[6-9]\\d{9}$",
                message = "Invalid Indian phone number. Must be 10 digits and can optionally start with +91 or 0."
        )
        String phone,

        @NotBlank(message="Invalid Insurance Type")
        InsuranceType insuranceType,

        @NotBlank(message = "Invalid Requirement")
        String requirements,

        @NotBlank(message = "Invalid Preferred Contact Time")
        LocalDateTime preferredContactTime) {
}
