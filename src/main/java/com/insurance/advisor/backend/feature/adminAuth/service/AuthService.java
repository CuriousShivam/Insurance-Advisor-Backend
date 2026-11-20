package com.insurance.advisor.backend.feature.adminAuth.service;

import com.insurance.advisor.backend.config.JWTUtil;
import com.insurance.advisor.backend.feature.adminAuth.dto.NewUserFields;
import com.insurance.advisor.backend.feature.adminAuth.entity.AdminUser;
import com.insurance.advisor.backend.feature.adminAuth.dto.AuthResponse;
import com.insurance.advisor.backend.feature.adminAuth.dto.LoginCredentials;
import com.insurance.advisor.backend.feature.adminAuth.repo.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminUserRepository userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.jwt.expiryInMinutes}")
    private String tokenExpiry;


    /**
     * Handles new admin registration with password encryption[cite: 66, 74].
     */
    public AuthResponse registerAdmin(NewUserFields userData) {
        System.out.println("Api : /register Post");
        AdminUser user = new AdminUser();
        user.setEmail(userData.getEmail());
        user.setUsername(userData.getUsername());
        user.setPassword(userData.getPassword());
        user.setFullName(userData.getFullName());
        user.setPhone(userData.getPhone());
        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer", tokenExpiry+" mins");
    }

    /**
     * Validates admin credentials and manages session creation [cite: 251, 765-766].
     */
    public AuthResponse login (LoginCredentials body) {
        System.out.println("Api : /login Post");
        try{
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email(), body.password());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.email());
            return new AuthResponse(token, "Bearer", tokenExpiry+" mins");
        } catch(AuthenticationException authExc){
            throw new RuntimeException("Invalid username/password.");
        }
    }
}