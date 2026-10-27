package com.insurance.advisor.backend.feature.adminAuth.service;

import com.insurance.advisor.backend.config.JWTUtil;
import com.insurance.advisor.backend.feature.adminAuth.entity.AdminUser;
import com.insurance.advisor.backend.feature.adminAuth.dto.AuthResponse;
import com.insurance.advisor.backend.feature.adminAuth.dto.LoginInCreeds;
import com.insurance.advisor.backend.feature.adminAuth.repo.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> registerAdmin(AdminUser user) {
        System.out.println("Api : /register Post");
        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer", tokenExpiry+" mins"));
    }

    /**
     * Validates admin credentials and manages session creation [cite: 251, 765-766].
     */
    public ResponseEntity<AuthResponse> login (LoginInCreeds body) {
        System.out.println("Api : /login Post");
        try{
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPasswordHash());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, "Bearer", tokenExpiry+" mins"));
        } catch(AuthenticationException authExc){
            throw new RuntimeException("Invalid username/password.");
        }
    }
}