package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface EnquiryRepository extends JpaRepository<Enquiry, UUID> {
    // Helps with Admin Dashboard filtering [cite: 66]
    List<Enquiry> findByFollowUpStatus(String status);
}