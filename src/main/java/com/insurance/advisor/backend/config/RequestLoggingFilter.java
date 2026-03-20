package com.insurance.advisor.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        // Wrap the response so we can read the body after the controller finishes
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // 1. LOG THE REQUEST
        System.out.println("============================================");

        System.out.println(">>> DEBUG LOG: Incoming Request");
        System.out.println("Method: " + req.getMethod());
        System.out.println("URI:    " + req.getRequestURI());

        System.out.println("Origin: " + req.getHeader("Origin"));
        System.out.println("============================================");


        // 2. PROCEED
        chain.doFilter(request, responseWrapper);

        // 3. LOG THE RESPONSE BODY
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseArray, responseWrapper.getCharacterEncoding());

        System.out.println("<<< DEBUG LOG: Outgoing Response");
        System.out.println("Status: " + responseWrapper.getStatus());
        System.out.println("Body:   " + (responseBody.length() > 500 ? responseBody.substring(0, 500) + "..." : responseBody));
        System.out.println("============================================");

        // CRITICAL: Copy the cached body back to the real response so the client receives it
        responseWrapper.copyBodyToResponse();
    }
}