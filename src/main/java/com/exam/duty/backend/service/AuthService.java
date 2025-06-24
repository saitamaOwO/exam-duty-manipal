package com.exam.duty.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.exam.duty.backend.dto.AuthRequest;
import com.exam.duty.backend.dto.AuthResponse;
import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.repository.StaffRepository;
import com.exam.duty.backend.security.JwtUtil;

@Service
public class AuthService {
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public AuthResponse authenticate(AuthRequest authRequest) {
        //Validate staff exists
        Optional<Staff> staffOpt = staffRepository.findByStaffId(authRequest.getStaffId());
        if (!staffOpt.isPresent()) {
            throw new BadCredentialsException("Invalid credentials");
        }
        
        Staff staff = staffOpt.get();
        //Determine roles based on staff type and academic rank
        List<String> roles = determineRoles(staff);
        
        //Generate JWT token
        String token = jwtUtil.generateJwtToken(staff.getStaffId(), roles);
        
        String staffName = staff.getFirstName() + " " + staff.getLastName();
        
        return new AuthResponse(token, staff.getStaffId(), staffName, roles);
    }
    
    private List<String> determineRoles(Staff staff) {
        //Determine roles based on staff type and academic rank
        if ("NON-TEACHING".equals(staff.getStaffType())) {
            return List.of("ADMIN");
        } else {
            //For teaching staff, determine based on academic rank
            String rank = staff.getAcademicRank();
            if (rank != null && rank.toLowerCase().contains("professor")) {
                return List.of("FACULTY", "HOD");
            } else {
                return List.of("FACULTY");
            }
        }
    }
}