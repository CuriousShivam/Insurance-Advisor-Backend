package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.Enquiry;
import com.insurance.advisor.backend.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EnquiryController {

    @Autowired
    private EnquiryRepository enquiryRepository;

    /**
     * PUBLIC ENDPOINT: UC-V03 Submit Consultation Enquiry
     * Allows anonymous visitors to send leads.
     */
    @PostMapping("/enquiries/submit")
    public ResponseEntity<String> submitEnquiry(@RequestBody Enquiry enquiry) {
        // Set default values as per proposal [cite: 340]
        enquiry.setFollowUpStatus("pending");
        enquiry.setPriorityLevel("normal");
        enquiry.setCreatedAt(LocalDateTime.now());

        enquiryRepository.save(enquiry);
        return ResponseEntity.ok("Enquiry submitted successfully! We will contact you soon.");
    }

    /**
     * ADMIN ONLY ENDPOINT: UC-A02 Manage Customer Enquiries
     * Fetches all leads for the admin dashboard.
     */
    @GetMapping("/admin/enquiries/all")
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        // In a real scenario, Spring Security ensures only 'ADMIN' role reaches here
        List<Enquiry> enquiries = enquiryRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(enquiries);
    }

    // Requirement 6.1.3: Data Deletion for Administrative Management
    @DeleteMapping("/admin/enquiries/{id}")
    public ResponseEntity<Void> deleteEnquiry(@PathVariable UUID id) {
            if (!enquiryRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            enquiryRepository.deleteById(id);
            System.out.println("DELETED: Enquiry ID " + id); // Visible in Xubuntu terminal
            return ResponseEntity.noContent().build();
        }
}