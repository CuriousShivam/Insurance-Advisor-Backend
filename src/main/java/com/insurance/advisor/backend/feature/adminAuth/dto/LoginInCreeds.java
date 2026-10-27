package com.insurance.advisor.backend.feature.adminAuth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginInCreeds {
    private String email;
    private String passwordHash;
}