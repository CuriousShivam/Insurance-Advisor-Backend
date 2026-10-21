package com.insurance.advisor.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@NoArgsConstructor
public class JWTUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiryInMinutes}")
    private String jwtExpiryInMinutes;

    public String generateToken(String email){
        System.out.println("JWTUTIL generateToken");
        Instant now = Instant.now();
        Instant expiry = now.plus(Long.parseLong(jwtExpiryInMinutes), ChronoUnit.MINUTES);
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("Backend Application")
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String validateTokenAndRetrieveSubject(String token) {
        try {
            //System.out.println("JWTUtil validateTokenAndRetrieveSubject");
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withSubject("User Details")
                    .withIssuer("Backend Application")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("email").asString();

        } catch (TokenExpiredException e) {
            System.out.println("Token verification failed: The token has expired.");
            throw e; // Or map to a custom security exception
        } catch (JWTVerificationException e) {
            System.out.println("Token verification failed: Invalid signature or structural tampering.");
            throw e;
        }
    }
}
