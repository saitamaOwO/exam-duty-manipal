package com.exam.duty.backend.security;


import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateJwtToken(String staffId, List<String> roles) {
        logger.debug("Generating JWT token for staff: {} with roles: {}", staffId, roles);
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        String token = Jwts.builder()
                .setSubject(staffId)
                .claim("roles", roles)
                .claim("preferred_username", staffId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        
        logger.debug("JWT token generated successfully for staff: {}, token length: {}", staffId, token.length());
        return token;
    }

    public String getStaffIdFromJwtToken(String token) {
        try {
            logger.debug("Extracting staff ID from token...");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            String staffId = claims.getSubject();
            logger.debug("Extracted staff ID from token: {}", staffId);
            return staffId;
        } catch (Exception e) {
            logger.error("Error extracting staff ID from token: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromJwtToken(String token) {
        try {
            logger.debug("Extracting roles from token...");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            List<String> roles = (List<String>) claims.get("roles");
            logger.debug("Extracted roles from token: {}", roles);
            return roles;
        } catch (Exception e) {
            logger.error("Error extracting roles from token: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            logger.debug("Validating JWT token of length: {}", authToken.length());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            
            Date expiration = claims.getExpiration();
            Date now = new Date();
            boolean isValid = expiration.after(now);
            
            logger.debug("JWT token validation result: {}, expires: {}, current time: {}", isValid, expiration, now);
            
            if (!isValid) {
                logger.warn("JWT token has expired. Expiry: {}, Current: {}", expiration, now);
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("JWT token validation failed: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    public static String getStaffIdFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public static List<String> getRolesFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .toList();
        }
        return List.of();
    }
}