package com.insurance.advisor.backend.repository;
import com.insurance.advisor.backend.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AdminUserRepository extends JpaRepository<AdminUser, UUID> {
    // Required for the Admin Login and OTP flow
    Optional<AdminUser> findByEmail(String email);
}