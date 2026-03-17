package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.AdminUser;
import com.insurance.advisor.backend.repository.AdminUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Endpoint for Admin Registration (One-time setup)
    @PostMapping("/register")
    public String register(@RequestBody AdminUser admin) {
        //System.out.print("Reached Post Mapping");
        // Encrypt password before saving [cite: 66, 74]
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
        repository.save(admin);
        return "Admin registered successfully!";
    }

    // Endpoint for Admin Login [cite: 249]
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminUser loginRequest, HttpServletRequest request) {
        Optional<AdminUser> admin = repository.findByEmail(loginRequest.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(loginRequest.getPasswordHash(), admin.get().getPasswordHash())) {
            // This line triggers the creation of the JSESSIONID cookie

            request.getSession(true);
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}