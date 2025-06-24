package com.exam.duty.backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.repository.StaffRepository;
import com.exam.duty.backend.repository.StaffRoleRepository;

@Service
public class StaffEligibilityService {
    
    private static final Logger logger = LoggerFactory.getLogger(StaffEligibilityService.class);
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private StaffRoleRepository staffRoleRepository;
    public boolean isEligibleForExamDuty(String staffId) {
        try {
            logger.debug("Checking eligibility for staff: {}", staffId);
            
            Optional<Staff> staffOpt = staffRepository.findByStaffId(staffId);
            if (!staffOpt.isPresent()) {
                logger.warn("Staff not found: {}", staffId);
                return false;
            }
            
            Staff staff = staffOpt.get();
            logger.debug("Staff found: {} - Type: {}", staffId, staff.getStaffType());
            
            // Only teaching staff are eligible
            if (!"TEACHING".equals(staff.getStaffType())) {
                logger.debug("Staff {} is not teaching staff", staffId);
                return false;
            }
            
            //Check if staff has excluded roles
            List<String> roles = getStaffRoles(staffId);
            logger.debug("Staff {} has roles: {}", staffId, roles);
            
            //HOD and Class Committee Chairpersons are excluded
            if (roles.contains("HOD") || 
                roles.contains("CLASS_COMMITTEE_CHAIRPERSON")) {
                logger.debug("Staff {} has excluded role", staffId);
                return false;
            }
            
            logger.debug("Staff {} is eligible", staffId);
            return true;
        } catch (Exception e) {
            logger.error("Error checking eligibility for staff {}: {}", staffId, e.getMessage(), e);
            return false;
        }
    }
    
    private List<String> getStaffRoles(String staffId) {
        try {
            return staffRoleRepository.findRoleNamesByStaffId(staffId);
        } catch (Exception e) {
            logger.error("Error getting roles for staff {}: {}", staffId, e.getMessage());
            return List.of();
        }
    }
    public String getIneligibilityReason(String staffId) {
        try {
            Optional<Staff> staffOpt = staffRepository.findByStaffId(staffId);
            if (!staffOpt.isPresent()) {
                return "Staff not found";
            }
            
            Staff staff = staffOpt.get();
            
            if (!"TEACHING".equals(staff.getStaffType())) {
                return "Only teaching staff are eligible for exam duties";
            }
            
            List<String> roles = getStaffRoles(staffId);
            
            if (roles.contains("HOD")) {
                return "HOD is not eligible for exam duties";
            }
            
            if (roles.contains("CLASS_COMMITTEE_CHAIRPERSON")) {
                return "Class Committee Chairpersons are not eligible for exam duties";
            }
            
            return "Staff is eligible";
        } catch (Exception e) {
            logger.error("Error getting ineligibility reason for staff {}: {}", staffId, e.getMessage());
            return "Error checking eligibility";
        }
    }
    
    public List<Staff> getEligibleStaff() {
        try {
            return staffRepository.findEligibleStaffForExamDuty();
        } catch (Exception e) {
            logger.error("Error getting eligible staff: {}", e.getMessage());
            return List.of();
        }
    }
    public boolean hasRole(String staffId, String roleName) {
        try {
            return staffRepository.hasRole(staffId, roleName);
        } catch (Exception e) {
            logger.error("Error checking role {} for staff {}: {}", roleName, staffId, e.getMessage());
            return false;
        }
    }
}