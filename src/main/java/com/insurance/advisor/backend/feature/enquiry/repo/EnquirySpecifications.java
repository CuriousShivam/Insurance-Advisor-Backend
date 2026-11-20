package com.insurance.advisor.backend.feature.enquiry.repo;

import com.insurance.advisor.backend.config.enums.FollowUpStatus;
import com.insurance.advisor.backend.config.enums.InsuranceType;
import com.insurance.advisor.backend.config.enums.PriorityLevel;
import com.insurance.advisor.backend.feature.enquiry.entity.Enquiry;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EnquirySpecifications {

    public static Specification<Enquiry> withFilters(
            FollowUpStatus status,
            InsuranceType type,
            PriorityLevel priority,
            String search
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Exact match for FollowUpStatus (if provided)
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("followUpStatus"), status));
            }

            // 2. Exact match for InsuranceType (if provided)
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("insuranceType"), type));
            }

            // 3. Exact match for PriorityLevel (if provided)
            if (priority != null) {
                predicates.add(criteriaBuilder.equal(root.get("priorityLevel"), priority));
            }

            // 4. Keyword search across fullName, email, and phone (case-insensitive)
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim().toLowerCase() + "%";

                Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), searchPattern);
                Predicate emailLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern);
                Predicate phoneLike = criteriaBuilder.like(root.get("phone"), "%" + search.trim() + "%");

                // (fullName LIKE %s% OR email LIKE %s% OR phone LIKE %s%)
                predicates.add(criteriaBuilder.or(nameLike, emailLike, phoneLike));
            }

            // Combines all active predicates with SQL 'AND'
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
