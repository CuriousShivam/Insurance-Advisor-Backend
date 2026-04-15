package com.insurance.advisor.backend.service;

import com.insurance.advisor.backend.model.AdminUser;
import com.insurance.advisor.backend.repository.AdminUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Handles new admin registration with password encryption[cite: 66, 74].
     */
    public void registerAdmin(AdminUser admin) {
        // Encrypt password using BCrypt before database persistence [cite: 764]
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
        repository.save(admin);
    }

    /**
     * Validates admin credentials and manages session creation [cite: 251, 765-766].
     */
    public boolean authenticate(AdminUser loginRequest, HttpServletRequest request) {
        Optional<AdminUser> admin = repository.findByEmail(loginRequest.getEmail());

        // Verify email existence and match hashed passwords [cite: 258, 764]
        if (admin.isPresent() && passwordEncoder.matches(loginRequest.getPasswordHash(), admin.get().getPasswordHash())) {
            // Trigger secure session creation and JSESSIONID cookie [cite: 66, 769]
            request.getSession(true);
            return true;
        }
        return false;
    }
}