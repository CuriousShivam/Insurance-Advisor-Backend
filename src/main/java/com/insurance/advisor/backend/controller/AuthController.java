package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.AdminUser;
import com.insurance.advisor.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint for Admin Registration (One-time setup)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AdminUser admin) {
        authService.registerAdmin(admin);
        return ResponseEntity.ok("Admin registered successfully!");
    }

    // Endpoint for Admin Login [cite: 249, 256]
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminUser loginRequest, HttpServletRequest request) {
        boolean isAuthenticated = authService.authenticate(loginRequest, request);

        if (isAuthenticated) {
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}