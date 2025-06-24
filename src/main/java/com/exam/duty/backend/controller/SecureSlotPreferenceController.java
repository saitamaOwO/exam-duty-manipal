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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.duty.backend.dto.SecureSlotPreferenceRequest;
import com.exam.duty.backend.dto.SlotPreferenceRequest;
import com.exam.duty.backend.dto.SlotResponse;
import com.exam.duty.backend.dto.StaffDutyInfo;
import com.exam.duty.backend.security.JwtUtil;
import com.exam.duty.backend.service.SlotPreferenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/secure/slot-preferences")
@CrossOrigin(origins = "*")
public class SecureSlotPreferenceController {
    
    private static final Logger logger = LoggerFactory.getLogger(SecureSlotPreferenceController.class);
    
    @Autowired
    private SlotPreferenceService slotPreferenceService;
    
    /*
     Get available slots for authenticated faculty member(faculty only)
    */
    @GetMapping("/available")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<List<SlotResponse>> getAvailableSlots(
            @RequestParam Integer academicYear,
            @RequestParam String term,
            Authentication authentication) {
        
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Getting available slots for staff: {}", staffId);
            List<SlotResponse> slots = slotPreferenceService.getAvailableSlots(staffId, academicYear, term);
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            logger.error("Error getting available slots: {}", e.getMessage());
            return ResponseEntity.badRequest().body(List.of());
        } catch (Exception e) {
            logger.error("Unexpected error getting available slots: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(List.of());
        }
    }
    
    /*
     View all slots(HOD, CCC, ADMIN can only view)
    */
    @GetMapping("/view/all")
    @PreAuthorize("hasAnyRole('HOD', 'CCC', 'ADMIN', 'FACULTY')")
    public ResponseEntity<List<SlotResponse>> viewAllSlots(
            @RequestParam Integer academicYear,
            @RequestParam String term,
            Authentication authentication) {
        
        try {
            List<SlotResponse> slots = slotPreferenceService.getAllSlotsForViewing(academicYear, term);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            logger.error("Error viewing all slots: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of());
        }
    }
    
    /*
     Select a slot preference(faculty only excludes HOD and CCC)
    */
    @PostMapping("/select")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Map<String, Object>> selectSlotPreference(
            @Valid @RequestBody SecureSlotPreferenceRequest request,
            Authentication authentication) {
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Staff {} selecting slot - Exam: {}, Slot: {}", staffId, request.getExamId(), request.getSlotId());
            
            //Convert to internal request format
            SlotPreferenceRequest internalRequest = new SlotPreferenceRequest(
                request.getExamId(), request.getSlotId(), staffId
            );
            
            String result = slotPreferenceService.selectSlotPreference(internalRequest);
            logger.debug("Slot selection result: {}", result);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", result,
                "examId", request.getExamId(),
                "slotId", request.getSlotId()
            ));
        } catch (RuntimeException e) {
            logger.error("Error selecting slot preference: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Unexpected error selecting slot preference: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "An error occurred while selecting slot preference"
            ));
        }
    }

    /*
     Remove a slot preference(faculty only excludes HOD and CCC)
    */
    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Map<String, Object>> removeSlotPreference(
            @Valid @RequestBody SecureSlotPreferenceRequest request,
            Authentication authentication) {
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Staff {} removing slot - Exam: {}, Slot: {}", staffId, request.getExamId(), request.getSlotId());
            
            //Convert to internal request format
            SlotPreferenceRequest internalRequest = new SlotPreferenceRequest(
                request.getExamId(), request.getSlotId(), staffId
            );
            
            String result = slotPreferenceService.removeSlotPreference(internalRequest);
            logger.debug("Slot removal result: {}", result);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", result,
                "examId", request.getExamId(),
                "slotId", request.getSlotId()
            ));
        } catch (RuntimeException e) {
            logger.error("Error removing slot preference: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Unexpected error removing slot preference: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "An error occurred while removing slot preference"
            ));
        }
    }
    
    /*
     Remove a selected slot by exam duty ID
    */
    @DeleteMapping("/remove/{examDutyId}")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Map<String, Object>> removeSlotPreferenceById(
            @PathVariable Integer examDutyId,
            Authentication authentication) {
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Staff {} removing slot by ID: {}", staffId, examDutyId);
            
            String result = slotPreferenceService.removeSlotPreferenceById(examDutyId, staffId);
            logger.debug("Slot removal by ID result: {}", result);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", result,
                "examDutyId", examDutyId
            ));
        } catch (RuntimeException e) {
            logger.error("Error removing slot preference by ID: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Unexpected error removing slot preference by ID: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "An error occurred while removing slot preference"
            ));
        }
    }
    
    /*
     Get staff duty information(All authenticated users can view)
    */
    @GetMapping("/duty-info")
    @PreAuthorize("hasAnyRole('FACULTY', 'HOD', 'CCC', 'ADMIN')")
    public ResponseEntity<StaffDutyInfo> getStaffDutyInfo(
            @RequestParam Integer academicYear,
            @RequestParam String term,
            Authentication authentication) {
        
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            StaffDutyInfo dutyInfo = slotPreferenceService.getStaffDutyInfoForViewing(staffId, academicYear, term);
            return ResponseEntity.ok(dutyInfo);
        } catch (RuntimeException e) {
            logger.error("Error getting staff duty info: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting staff duty info: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /*
     Get authenticated staff's selected slots(All authenticated users can view their own)
    */
    @GetMapping("/selected")
    @PreAuthorize("hasAnyRole('FACULTY', 'HOD', 'CCC', 'ADMIN')")
    public ResponseEntity<List<SlotResponse>> getStaffSelectedSlots(Authentication authentication) {
        try {
            String staffId = JwtUtil.getStaffIdFromAuthentication(authentication);
            logger.debug("Getting selected slots for staff: {}", staffId);
            List<SlotResponse> slots = slotPreferenceService.getStaffSelectedSlotsForViewing(staffId);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            logger.error("Error getting staff selected slots: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of());
        }
    }
    
    /*
     Admin endpoint to view all slot preferences with staff details
    */
    @GetMapping("/admin/all-with-staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SlotResponse>> getAllSlotPreferencesWithStaff(
            @RequestParam Integer academicYear,
            @RequestParam String term,
            Authentication authentication) {
        
        try {
            List<SlotResponse> slots = slotPreferenceService.getAllSlotsWithStaffDetails(academicYear, term);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            logger.error("Error getting all slot preferences with staff: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of());
        }
    }
}