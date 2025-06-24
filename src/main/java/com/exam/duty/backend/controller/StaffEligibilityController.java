package com.exam.duty.backend.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.security.JwtUtil;
import com.exam.duty.backend.service.StaffEligibilityService;

@RestController
@RequestMapping("/api/secure/staff-eligibility")
@CrossOrigin(origins = "*")
public class StaffEligibilityController {
    
    private static final Logger logger = LoggerFactory.getLogger(StaffEligibilityController.class);
    
    @Autowired
    private StaffEligibilityService staffEligibilityService;
    
    @GetMapping("/check")
    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN', 'HOD', 'CCC')")
    public ResponseEntity<Map<String, Object>> checkEligibility(Authentication authentication) {
        try {
            logger.debug("Checking eligibility for authentication: {}", authentication.getName());
            
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Staff ID extracted: {}", staffId);
            
            boolean isEligible = staffEligibilityService.isEligibleForExamDuty(staffId);
            String reason = staffEligibilityService.getIneligibilityReason(staffId);
            
            logger.debug("Eligibility result - Staff: {}, Eligible: {}, Reason: {}", staffId, isEligible, reason);
            
            return ResponseEntity.ok(Map.of(
                "eligible", isEligible,
                "reason", reason,
                "staffId", staffId
            ));
        } catch (Exception e) {
            logger.error("Error checking eligibility: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to check eligibility",
                "message", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/eligible-staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Staff>> getEligibleStaff() {
        try {
            List<Staff> eligibleStaff = staffEligibilityService.getEligibleStaff();
            return ResponseEntity.ok(eligibleStaff);
        } catch (Exception e) {
            logger.error("Error getting eligible staff: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}