package com.exam.duty.backend.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        logger.info("=== JWT FILTER PROCESSING ===");
        logger.info("Request: {} {}", method, requestURI);
        
        try {
            String jwt = parseJwt(request);
            logger.info("JWT Token extracted: {}", jwt != null ? "Present (length: " + jwt.length() + ")" : "Not Present");
            
            if (jwt != null && !jwt.trim().isEmpty()) {
                logger.info("Validating JWT token...");
                boolean isValid = jwtUtil.validateJwtToken(jwt);
                logger.info("JWT validation result: {}", isValid);
            
                if (isValid) {
                    String staffId = jwtUtil.getStaffIdFromJwtToken(jwt);
                    List<String> roles = jwtUtil.getRolesFromJwtToken(jwt);
                
                    logger.info("JWT validation successful - Staff ID: {}, Roles: {}", staffId, roles);
                
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> {
                            String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                            logger.info("Adding authority: {}", authority);
                            return new SimpleGrantedAuthority(authority);
                        })
                        .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(staffId, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication set successfully for user: {} with authorities: {}", 
                           staffId, authorities);
                } else {
                    logger.warn("JWT token validation failed");
                }
            } else {
                logger.info("No valid JWT token found in request");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication for request {} {}: {}", method, requestURI, e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        logger.info("=== JWT FILTER COMPLETE ===");
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        logger.info("Raw Authorization header: '{}'", headerAuth);

        if (StringUtils.hasText(headerAuth)) {
            logger.info("Authorization header has text, length: {}", headerAuth.length());
            
            if (headerAuth.startsWith("Bearer ")) {
                String token = headerAuth.substring(7).trim();
                logger.info("Extracted token length after Bearer: {}", token.length());
                logger.info("Token starts with: {}", token.length() > 10 ? token.substring(0, 10) + "..." : token);
                return token;
            } else {
                logger.warn("Authorization header does not start with 'Bearer '");
            }
        } else {
            logger.info("Authorization header is empty or null");
        }

        return null;
    }
}