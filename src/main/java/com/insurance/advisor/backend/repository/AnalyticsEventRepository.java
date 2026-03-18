package com.insurance.advisor.backend.repository;

import com.insurance.advisor.backend.model.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.List;

public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, UUID> {

    // Native query to calculate Conversion Rate for a specific Insurance Type
    @Query(value = "SELECT " +
            "(COUNT(DISTINCT e.id) * 100.0 / NULLIF(COUNT(DISTINCT a.id), 0)) " +
            "FROM analytics_events a " +
            "LEFT JOIN enquiries e ON a.event_data->>'insurance_type' = e.insurance_type " +
            "WHERE a.event_type = 'PAGE_VIEW' AND a.event_name = :serviceName",
            nativeQuery = true)
    Double calculateConversionRateByService(@Param("serviceName") String serviceName);
}