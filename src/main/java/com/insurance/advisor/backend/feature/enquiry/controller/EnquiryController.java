package com.insurance.advisor.backend.feature.enquiry.controller;

import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import com.insurance.advisor.backend.feature.enquiry.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/enquiries/submit")
    public ResponseEntity<String> submitEnquiry(@RequestBody Enquiry enquiry) {
        // Set default values as per proposal
        return enquiryService.submitEnquiry(enquiry);
    }

    /**
     * ADMIN ONLY ENDPOINT: UC-A02 Manage Customer Enquiries
     * Fetches all leads for the admin dashboard.
     */
    @GetMapping("/admin/enquiries/all")
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        // In a real scenario, Spring Security ensures only 'ADMIN' role reaches here
        return enquiryService.getAllEnquiries();
    }

    // Requirement 6.1.3: Data Deletion for Administrative Management
    @DeleteMapping("/admin/enquiries/{id}")
    public ResponseEntity<Void> deleteEnquiry(@PathVariable UUID id) {
            return enquiryService.deleteEnquiry(id);
        }
}