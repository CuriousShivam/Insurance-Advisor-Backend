package com.insurance.advisor.backend.feature.enquiry.repo;

import com.insurance.advisor.backend.feature.enquiry.dto.EnquiryDetailResponse;
import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnquiryRepository extends JpaRepository<Enquiry, UUID>, JpaSpecificationExecutor<Enquiry> {
    // Helps with Admin Dashboard filtering [cite: 66]
    List<Enquiry> findByFollowUpStatus(String status);

    List<Enquiry> findAllByOrderByCreatedAtDesc();


    Optional<EnquiryDetailResponse> findProjectedById(@Param("id") UUID id);
}