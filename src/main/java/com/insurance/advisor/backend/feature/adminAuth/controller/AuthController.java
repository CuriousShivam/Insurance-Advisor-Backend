package com.insurance.advisor.backend.feature.adminAuth.controller;

import com.insurance.advisor.backend.feature.adminAuth.entity.AdminUser;
import com.insurance.advisor.backend.feature.adminAuth.dto.AuthResponse;
import com.insurance.advisor.backend.feature.adminAuth.dto.LoginInCreeds;
import com.insurance.advisor.backend.feature.adminAuth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerHandler(@RequestBody AdminUser user){
        return authService.registerAdmin(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginInCreeds body
    ){
        return authService.login(body);
    }
}