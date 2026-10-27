package com.insurance.advisor.backend.feature.enquiry.repo;

import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnquiryRepository extends JpaRepository<Enquiry, UUID> {
    // Helps with Admin Dashboard filtering [cite: 66]
    List<Enquiry> findByFollowUpStatus(String status);

    List<Enquiry> findAllByOrderByCreatedAtDesc();
}