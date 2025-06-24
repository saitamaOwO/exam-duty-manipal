package com.exam.duty.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.duty.backend.dto.SlotPreferenceRequest;
import com.exam.duty.backend.dto.SlotResponse;
import com.exam.duty.backend.dto.StaffDutyInfo;
import com.exam.duty.backend.service.SlotPreferenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/slot-preferences")
@CrossOrigin(origins = "*")
public class SlotPreferenceController {
    
    @Autowired
    private SlotPreferenceService slotPreferenceService;
    
    @GetMapping("/available")
    public ResponseEntity<List<SlotResponse>> getAvailableSlots(
            @RequestParam String staffId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {
        
        try {
            List<SlotResponse> slots = slotPreferenceService.getAvailableSlots(staffId, academicYear, term);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/select")
    public ResponseEntity<String> selectSlotPreference(@Valid @RequestBody SlotPreferenceRequest request) {
        try {
            String result = slotPreferenceService.selectSlotPreference(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while selecting slot preference");
        }
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSlotPreference(@Valid @RequestBody SlotPreferenceRequest request) {
        try {
            String result = slotPreferenceService.removeSlotPreference(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while removing slot preference");
        }
    }
    
    @GetMapping("/duty-info")
    public ResponseEntity<StaffDutyInfo> getStaffDutyInfo(
            @RequestParam String staffId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {
        
        try {
            StaffDutyInfo dutyInfo = slotPreferenceService.getStaffDutyInfo(staffId, academicYear, term);
            return ResponseEntity.ok(dutyInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/selected")
    public ResponseEntity<List<SlotResponse>> getStaffSelectedSlots(@RequestParam String staffId) {
        try {
            List<SlotResponse> slots = slotPreferenceService.getStaffSelectedSlots(staffId);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}