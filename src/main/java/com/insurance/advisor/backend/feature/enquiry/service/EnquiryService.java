package com.insurance.advisor.backend.feature.enquiry.service;

import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import com.insurance.advisor.backend.feature.enquiry.repo.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnquiryService {
    @Autowired
    private EnquiryRepository enquiryRepository;

    /**
     * PUBLIC ENDPOINT: Submit Consultation Enquiry
     * Allows anonymous visitors to send leads.
     */
    public ResponseEntity<String> submitEnquiry( Enquiry enquiry) {
        // Set default values as per proposal [cite: 340]
        enquiry.setFollowUpStatus(FollowUpStatus.NEW);
        enquiry.setPriorityLevel(PriorityLevel.LOW);
        //enquiry.setCreatedAt(LocalDateTime.now());

        enquiryRepository.save(enquiry);
        return ResponseEntity.ok("Enquiry submitted successfully! We will contact you soon.");
    }

    /**
     * ADMIN ONLY ENDPOINT: UC-A02 Manage Customer Enquiries
     * Fetches all leads for the admin dashboard.
     */
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        // In a real scenario, Spring Security ensures only 'ADMIN' role reaches here
        List<Enquiry> enquiries = enquiryRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(enquiries);
    }

    // Requirement 6.1.3: Data Deletion for Administrative Management
    public ResponseEntity<Void> deleteEnquiry( UUID id) {
        if (!enquiryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enquiryRepository.deleteById(id);
        System.out.println("DELETED: Enquiry ID " + id); // Visible in Xubuntu terminal
        return ResponseEntity.noContent().build();
    }
}
