package com.insurance.advisor.backend.feature.adminAuth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
public class NewUserFields {

        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        @NotBlank(message = "Full name is required")
        private String fullName;

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^(?:\\+91|0)?[6-9]\\d{9}$",
                message = "Invalid Indian phone number. Must be 10 digits and can optionally start with +91 or 0."
        )
        private String phone;
}
