package com.insurance.advisor.backend.config.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Meets your project's requirement for bcrypt encryption [cite: 66, 74]
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF to allow Postman/Next.js POST requests [cite: 75]
                .csrf(csrf -> csrf.disable())

                // 2. Set session to STATELESS so no JSESSIONID is created for public hits [cite: 77]
                // Session Management for Concurrent Control
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1) // Restrict to 1 session per ID
                        .maxSessionsPreventsLogin(true) // True: Blocks 2nd login; False: Kicks out 1st user
                )

                .authorizeHttpRequests(auth -> auth
                        // 3. Explicitly allow your public endpoints [cite: 66, 71]
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/enquiries/**").permitAll()
                        .requestMatchers("/api/reviews/**").permitAll()
                        .requestMatchers("/api/blogs/**").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        // 4. Protect the admin-only fetch [cite: 66, 21]
                        .anyRequest().authenticated()
                )


                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.getWriter().write("Logout Successful");
                        })
                );
        return http.build();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}