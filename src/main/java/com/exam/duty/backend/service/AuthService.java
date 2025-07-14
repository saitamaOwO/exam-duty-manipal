package com.exam.duty.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.exam.duty.backend.dto.AuthRequest;
import com.exam.duty.backend.dto.AuthResponse;
import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.repository.StaffRepository;
import com.exam.duty.backend.security.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest authRequest) {
        logger.debug("Attempting to authenticate staff: {}", authRequest.getStaffId());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getStaffId(), authRequest.getPassword())
            );
            Optional<Staff> staffOpt = staffRepository.findByStaffId(authRequest.getStaffId());
            if (!staffOpt.isPresent()) {
                logger.error("Authenticated staff ID {} not found in repository.", authRequest.getStaffId());
                throw new BadCredentialsException("Staff details not found after authentication.");
            }

            Staff staff = staffOpt.get();
            List<String> roles = authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .map(role -> role.replace("ROLE_", ""))
                                    .collect(Collectors.toList());

            // Generate JWT token
            String token = jwtUtil.generateJwtToken(staff.getStaffId(), roles);

            String staffName = staff.getFirstName() + " " + staff.getLastName();

            logger.info("Authentication successful for staff: {}", staff.getStaffId());
            return new AuthResponse(token, staff.getStaffId(), staffName, roles);

        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for staff {}: Invalid credentials", authRequest.getStaffId());
            throw new BadCredentialsException("Invalid staff ID or password.");
        } catch (Exception e) {
            logger.error("An unexpected error occurred during authentication for staff {}: {}", authRequest.getStaffId(), e.getMessage(), e);
            throw new RuntimeException("Authentication failed due to an internal error.", e);
        }
    }
}
