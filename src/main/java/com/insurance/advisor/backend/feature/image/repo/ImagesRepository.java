package com.insurance.advisor.backend.feature.image.repo;

import com.insurance.advisor.backend.feature.image.entity.Images; // Ensure this path matches your model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ImagesRepository extends JpaRepository<Images, UUID> {
}