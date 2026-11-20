package com.insurance.advisor.backend.feature.enquiry.service;

import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.feature.enquiry.dto.EnquiryDetailResponse;
import com.insurance.advisor.backend.feature.enquiry.repo.EnquirySpecifications;
import com.insurance.advisor.backend.feature.enquiry.dto.EnquirySummaryResponse;
import com.insurance.advisor.backend.feature.enquiry.dto.NewEnquiryFields;
import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import com.insurance.advisor.backend.feature.enquiry.repo.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EnquiryService {
    @Autowired
    private EnquiryRepository enquiryRepository;

    /**
     * PUBLIC ENDPOINT: Submit Consultation Enquiry
     * Allows anonymous visitors to send leads.
     */
    public String submitEnquiry( NewEnquiryFields newEnquiry) {

        Enquiry enquiry = new Enquiry();
        enquiry.setFullName(newEnquiry.fullName());
        enquiry.setEmail(newEnquiry.email());
        enquiry.setPhone(newEnquiry.phone());
        enquiry.setInsuranceType(newEnquiry.insuranceType());
        enquiry.setRequirements(newEnquiry.requirements());
        enquiry.setPreferredContactTime(newEnquiry.preferredContactTime());
        enquiry.setFollowUpStatus(FollowUpStatus.NEW);
        enquiry.setPriorityLevel(PriorityLevel.MEDIUM);
        //enquiry.setCreatedAt(LocalDateTime.now());

        enquiryRepository.save(enquiry);
        return "Enquiry submitted successfully! We will contact you soon.";
    }


    public Page<EnquirySummaryResponse> getEnquiries(FollowUpStatus status,
                                                     InsuranceType type,
                                                     PriorityLevel priority,
                                                     String search,
                                                     int page,
                                                     int size
                                      ) {

        Sort sort = Sort.by(Sort.Direction.DESC, search);
        Pageable pageable = PageRequest.of(page, size, sort );
        Specification<Enquiry> spec = EnquirySpecifications.withFilters(status, type, priority, search);

        return enquiryRepository.findAll(spec, pageable)
                .map(e -> new EnquirySummaryResponse(
                        e.getId(),
                        e.getFullName(),
                        e.getPhone(),
                        e.getEmail(),
                        e.getInsuranceType(),
                        e.getFollowUpStatus(),
                        e.getPriorityLevel(),
                        e.getCreatedAt()
                ));
    }

    // Requirement 6.1.3: Data Deletion for Administrative Management
    public String deleteEnquiry( UUID id) {
        if (!enquiryRepository.existsById(id)) {
            return "Not available to delete";
        }
        enquiryRepository.deleteById(id);
        System.out.println("DELETED: Enquiry ID " + id); // Visible in Xubuntu terminal
        return "Data deleted successfully";
    }

    public EnquiryDetailResponse getEnquiryById(UUID id) {
        Optional<EnquiryDetailResponse> data = enquiryRepository.findProjectedById(id);
        return data.orElse(null);
    }
}
