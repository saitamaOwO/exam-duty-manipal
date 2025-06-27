package com.exam.duty.backend.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.duty.backend.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/debug")
@CrossOrigin(origins = "*")
public class DebugController {
    
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Debug endpoint to check authentication status
     */
    @GetMapping("/auth-status")
    public ResponseEntity<Map<String, Object>> checkAuthStatus(HttpServletRequest request) {
        logger.info("=== DEBUG AUTH STATUS ===");
        
        // Check Authorization header
        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: '{}'", authHeader);
        
        // Check SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("SecurityContext authentication: {}", auth != null ? auth.getClass().getSimpleName() : "null");
        
        if (auth != null) {
            logger.info("Authentication details - Name: {}, Authenticated: {}, Authorities: {}", 
                       auth.getName(), auth.isAuthenticated(), auth.getAuthorities());
        }
        
        // Try to parse JWT manually
        String token = null;
        boolean tokenValid = false;
        String staffIdFromToken = null;
        String errorMessage = null;
        
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            logger.info("Extracted token length: {}", token.length());
            logger.info("Token preview: {}...", token.length() > 20 ? token.substring(0, 20) : token);
            
            try {
                tokenValid = jwtUtil.validateJwtToken(token);
                logger.info("Manual JWT validation: {}", tokenValid);
                
                if (tokenValid) {
                    staffIdFromToken = jwtUtil.getStaffIdFromJwtToken(token);
                    logger.info("Staff ID from token: {}", staffIdFromToken);
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.error("Manual JWT validation error: {}", e.getMessage(), e);
            }
        }
        
        return ResponseEntity.ok(Map.of(
            "authHeaderPresent", authHeader != null,
            "authHeaderValue", authHeader != null ? authHeader.substring(0, Math.min(20, authHeader.length())) + "..." : "null",
            "tokenLength", token != null ? token.length() : 0,
            "tokenValid", tokenValid,
            "staffIdFromToken", staffIdFromToken != null ? staffIdFromToken : "null",
            "securityContextAuth", auth != null,
            "authenticated", auth != null && auth.isAuthenticated(),
            "principal", auth != null ? auth.getName() : "null",
            "authorities", auth != null ? auth.getAuthorities().toString() : "null",
            "error", errorMessage != null ? errorMessage : "none"
        ));
    }
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        return ResponseEntity.ok(Map.of(
            "message", "pong",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
}