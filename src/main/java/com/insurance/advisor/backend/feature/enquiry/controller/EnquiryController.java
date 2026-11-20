package com.insurance.advisor.backend.feature.enquiry.controller;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import com.insurance.advisor.backend.feature.enquiry.dto.EnquiryDetailResponse;
import com.insurance.advisor.backend.feature.enquiry.dto.EnquiryResponse;
import com.insurance.advisor.backend.feature.enquiry.dto.EnquirySummaryResponse;
import com.insurance.advisor.backend.feature.enquiry.dto.NewEnquiryFields;
import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import com.insurance.advisor.backend.feature.enquiry.service.EnquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    /**
     * PUBLIC ENDPOINT: UC-V03 Submit Consultation Enquiry
     * Allows anonymous visitors to send leads.
     */
    @PostMapping("/enquiry/submit")
    public ResponseEntity<String> submitEnquiry(@Valid @RequestBody NewEnquiryFields enquiry) {
        // Set default values as per proposal
        String res = enquiryService.submitEnquiry(enquiry);
        return ResponseEntity.ok(res);
    }

    /**
     * ADMIN ONLY ENDPOINT: UC-A02 Manage Customer Enquiries
     * Fetches all leads for the admin dashboard.
     */
    @GetMapping("/admin/enquiry")
    public ResponseEntity<Page<EnquirySummaryResponse>> getAllEnquiries(
            @RequestParam(required = false) FollowUpStatus status,
            @RequestParam(required = false) InsuranceType type,
            @RequestParam(required = false) PriorityLevel priority,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // In a real scenario, Spring Security ensures only 'ADMIN' role reaches here
        return ResponseEntity.ok(enquiryService.getEnquiries(status, type, priority, search, page, size));
    }

    @GetMapping("/admin/enquiry/{id}")
    public ResponseEntity<EnquiryDetailResponse> getEnquiryById(@PathVariable UUID id) {
        return ResponseEntity.ok(enquiryService.getEnquiryById(id));
    }

    // Requirement 6.1.3: Data Deletion for Administrative Management
    @DeleteMapping("/admin/enquiries/{id}")
    public ResponseEntity<String> deleteEnquiry(@PathVariable UUID id) {
        enquiryService.deleteEnquiry(id);
        EnquiryResponse.EnquiryStatusUpdateRequest
        return ResponseEntity.noContent().build();
        }
}