package com.insurance.advisor.backend.service;

import com.insurance.advisor.backend.model.AnalyticsEvent;
import com.insurance.advisor.backend.repository.AnalyticsEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsEventRepository analyticsRepository;

    /**
     * Processes and stores the analytics event.
     * Separates the business logic (setting IP and User-Agent) from the API layer.
     */
    public void processAndLogEvent(AnalyticsEvent event, HttpServletRequest request) {
        // Business logic: Extracting request metadata [cite: 319-323]
        event.setIpAddress(request.getRemoteAddr());
        event.setUserAgent(request.getHeader("User-Agent"));

        // Save to PostgreSQL database via Repository [cite: 378, 383]
        analyticsRepository.save(event);
    }
}