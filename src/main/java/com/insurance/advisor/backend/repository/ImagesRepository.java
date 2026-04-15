package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.Images; // Ensure this path matches your model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ImagesRepository extends JpaRepository<Images, UUID> {
}