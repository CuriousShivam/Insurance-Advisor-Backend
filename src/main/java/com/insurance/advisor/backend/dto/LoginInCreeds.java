package com.insurance.advisor.backend.dto;

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