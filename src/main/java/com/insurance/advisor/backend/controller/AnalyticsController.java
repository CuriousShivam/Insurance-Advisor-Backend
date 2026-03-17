package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.AnalyticsEvent;
import com.insurance.advisor.backend.repository.AnalyticsEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsEventRepository analyticsRepository;

    @PostMapping("/log")
    public void logEvent(@RequestBody AnalyticsEvent event, HttpServletRequest request) {
        event.setIpAddress(request.getRemoteAddr());
        event.setUserAgent(request.getHeader("User-Agent"));
        analyticsRepository.save(event);
    }
}