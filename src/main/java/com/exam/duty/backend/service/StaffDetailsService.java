package com.exam.duty.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.repository.StaffRepository;

@Service
public class StaffDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String staffId) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByStaffId(staffId)
                .orElseThrow(() -> new UsernameNotFoundException("Staff not found with ID: " + staffId));

        List<String> roles = determineRoles(staff);
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new User(staff.getStaffId(), staff.getPassword(), authorities);
    }

    private List<String> determineRoles(Staff staff) {
        String academicRankName = "";
        if (staff.getAcademicRank() != null && staff.getAcademicRank().getName() != null) {
            academicRankName = staff.getAcademicRank().getName().toLowerCase();
        }
        if ("NON-TEACHING".equals(staff.getStaffType())) {
            return List.of("ADMIN");
        } else {
            if (academicRankName.contains("professor")) {
                return List.of("FACULTY", "HOD");
            } else {
                return List.of("FACULTY");
            }
        }
    }
}
