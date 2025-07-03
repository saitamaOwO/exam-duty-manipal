package com.exam.duty.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.duty.backend.dto.SlotPreferenceRequest;
import com.exam.duty.backend.dto.SlotResponse;
import com.exam.duty.backend.dto.StaffDutyInfo;
import com.exam.duty.backend.entity.Course;
import com.exam.duty.backend.entity.Exam;
import com.exam.duty.backend.entity.ExamSlot;
import com.exam.duty.backend.entity.Staff;
import com.exam.duty.backend.entity.StaffDuty;
import com.exam.duty.backend.repository.ExamRepository;
import com.exam.duty.backend.repository.ExamSlotRepository;
import com.exam.duty.backend.repository.StaffDutyRepository;
import com.exam.duty.backend.repository.StaffRepository;

@Service
@Transactional
public class SlotPreferenceService {
    
    private static final Logger logger = LoggerFactory.getLogger(SlotPreferenceService.class);
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ExamSlotRepository examSlotRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private StaffDutyRepository staffDutyRepository;
    
    @Autowired
    private StaffEligibilityService staffEligibilityService;
    
    public List<SlotResponse> getAvailableSlots(String staffId, Integer academicYear, String term) {
        // check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(staffId)) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(staffId));
        }
        
        List<Exam> exams = examRepository.findExamsForSlotGeneration(academicYear, term);
        List<SlotResponse> slots = new ArrayList<>();
        
        // Get staff's teaching courses to exclude
        Optional<Staff> staffOpt = staffRepository.findByStaffId(staffId);
        List<String> teachingCourses = new ArrayList<>();
        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            if (staff.getCourses() != null) {
                teachingCourses = staff.getCourses().stream()
                    .map(Course::getCourseCode)
                    .collect(Collectors.toList());
            }
            logger.debug("Staff {} teaches courses: {}", staffId, teachingCourses);
        }
        
        for (Exam exam : exams) {
            logger.debug("Processing exam for course: {}", exam.getCourseCode());
            
            // Generate slots for each exam based on required duties
            int totalSlots = exam.getNoOfInvigilators() + exam.getNoOfRelievers() + exam.getNoOfSquads();
            
            for (int slotId = 1; slotId <= totalSlots; slotId++) {
                SlotResponse slot = new SlotResponse();
                slot.setExamId(exam.getExamId());
                slot.setSlotId(slotId);
                slot.setCourseCode(exam.getCourseCode());
                slot.setCourseName(exam.getCourse() != null ? exam.getCourse().getCourseName() : "");
                slot.setExamDate(exam.getExamDate());
                slot.setTime(exam.getTime());
                
                // Check if slot is already taken
                Optional<ExamSlot> existingSlot = examSlotRepository.findByExamIdAndSlotId(exam.getExamId(), slotId);
                
                if (existingSlot.isPresent()) {
                    if (existingSlot.get().getPreferredBy().equals(staffId)) {
                        slot.setStatus("SELECTED");
                        slot.setCanSelect(true);
                        slot.setReason("You have selected this slot");
                    } else {
                        slot.setStatus("OCCUPIED");
                        slot.setPreferredBy(existingSlot.get().getPreferredBy());
                        slot.setCanSelect(false);
                        slot.setReason("Already selected by another faculty");
                    }
                } else {
                    slot.setStatus("AVAILABLE");
                    
                    // Check if staff can select this slot
                    boolean canSelect = true;
                    String reason = "";
                    
                    // Rule: Cannot select course they teach
                    if (teachingCourses.contains(exam.getCourseCode())) {
                        canSelect = false;
                        reason = "Cannot select exam for course you teach";
                        logger.debug("Staff {} cannot select {} - teaches this course", staffId, exam.getCourseCode());
                    }
                    
                    // Rule: Cannot select multiple slots on same day
                    if (canSelect) {
                        List<ExamSlot> slotsOnSameDay = examSlotRepository.findByStaffIdAndExamDate(staffId, exam.getExamDate());
                        if (!slotsOnSameDay.isEmpty()) {
                            canSelect = false;
                            reason = "Cannot select multiple slots on the same day";
                        }
                    }
                    
                    slot.setCanSelect(canSelect);
                    slot.setReason(reason);
                }
                
                slots.add(slot);
            }
        }
        
        return slots;
    }
    
    /*
     Get all slots for viewing (HOD, CCC, Admin can view but not select)
     */
    public List<SlotResponse> getAllSlotsForViewing(Integer academicYear, String term) {
        List<Exam> exams = examRepository.findExamsForSlotGeneration(academicYear, term);
        List<SlotResponse> slots = new ArrayList<>();
        
        for (Exam exam : exams) {
            int totalSlots = exam.getNoOfInvigilators() + exam.getNoOfRelievers() + exam.getNoOfSquads();
            
            for (int slotId = 1; slotId <= totalSlots; slotId++) {
                SlotResponse slot = new SlotResponse();
                slot.setExamId(exam.getExamId());
                slot.setSlotId(slotId);
                slot.setCourseCode(exam.getCourseCode());
                slot.setCourseName(exam.getCourse() != null ? exam.getCourse().getCourseName() : "");
                slot.setExamDate(exam.getExamDate());
                slot.setTime(exam.getTime());
                
                // Check if slot is taken
                Optional<ExamSlot> existingSlot = examSlotRepository.findByExamIdAndSlotId(exam.getExamId(), slotId);
                
                if (existingSlot.isPresent()) {
                    slot.setStatus("OCCUPIED");
                    slot.setPreferredBy(existingSlot.get().getPreferredBy());
                    slot.setCanSelect(false);
                    slot.setReason("View only - slot taken");
                } else {
                    slot.setStatus("AVAILABLE");
                    slot.setCanSelect(false);
                    slot.setReason("View only access");
                }
                
                slots.add(slot);
            }
        }
        
        return slots;
    }
    
    /*
     Get all slots with staff details (Admin only)
     */
    public List<SlotResponse> getAllSlotsWithStaffDetails(Integer academicYear, String term) {
        List<SlotResponse> slots = getAllSlotsForViewing(academicYear, term);
        
        // Add staff names for occupied slots
        for (SlotResponse slot : slots) {
            if ("OCCUPIED".equals(slot.getStatus()) && slot.getPreferredBy() != null) {
                Optional<Staff> staffOpt = staffRepository.findByStaffId(slot.getPreferredBy());
                if (staffOpt.isPresent()) {
                    Staff staff = staffOpt.get();
                    slot.setReason("Selected by: " + staff.getFirstName() + " " + staff.getLastName());
                }
            }
        }
        
        return slots;
    }
    
    @Transactional
    public String selectSlotPreference(SlotPreferenceRequest request) {
        logger.debug("Selecting slot preference - Staff: {}, Exam: {}, Slot: {}", 
                    request.getStaffId(), request.getExamId(), request.getSlotId());
        
        // Check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(request.getStaffId())) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(request.getStaffId()));
        }
        
        // Validate staff exists
        Optional<Staff> staffOpt = staffRepository.findByStaffId(request.getStaffId());
        if (!staffOpt.isPresent()) {
            throw new RuntimeException("Staff not found");
        }
        
        // Validate exam exists
        Optional<Exam> examOpt = examRepository.findById(request.getExamId());
        if (!examOpt.isPresent()) {
            throw new RuntimeException("Exam not found");
        }
        
        Exam exam = examOpt.get();
        Staff staff = staffOpt.get();
        
        // Check if slot is already taken
        Optional<ExamSlot> existingSlot = examSlotRepository.findByExamIdAndSlotId(request.getExamId(), request.getSlotId());
        if (existingSlot.isPresent()) {
            if (!existingSlot.get().getPreferredBy().equals(request.getStaffId())) {
                throw new RuntimeException("Slot already selected by another faculty");
            } else {
                return "Slot already selected by you";
            }
        }
        
        // Check if staff teaches this course
        if (staff.getCourses() != null) {
            List<String> teachingCourses = staff.getCourses().stream()
                .map(Course::getCourseCode)
                .collect(Collectors.toList());
            
            if (teachingCourses.contains(exam.getCourseCode())) {
                throw new RuntimeException("Cannot select exam for course you teach");
            }
        }
        
        // Check if staff already has a slot on the same day
        List<ExamSlot> slotsOnSameDay = examSlotRepository.findByStaffIdAndExamDate(request.getStaffId(), exam.getExamDate());
        if (!slotsOnSameDay.isEmpty()) {
            throw new RuntimeException("Cannot select multiple slots on the same day");
        }
        
        // Check if staff has reached duty limit
        StaffDutyInfo dutyInfo = getStaffDutyInfo(request.getStaffId(), exam.getAcademicYear(), exam.getTerm());
        if (dutyInfo.getRemainingDuties() <= 0) {
            throw new RuntimeException("You have already selected all required duties");
        }
        
        // Create new slot preference
        ExamSlot newSlot = new ExamSlot();
        newSlot.setExamId(request.getExamId());
        newSlot.setSlotId(request.getSlotId());
        newSlot.setPreferredBy(request.getStaffId());
        newSlot.setPreferredDate(LocalDateTime.now());
        
        examSlotRepository.save(newSlot);
        logger.debug("Slot preference saved successfully for staff: {}", request.getStaffId());
        
        return "Slot preference saved successfully";
    }
    
    @Transactional
    public String removeSlotPreference(SlotPreferenceRequest request) {
        logger.debug("Removing slot preference - Staff: {}, Exam: {}, Slot: {}", 
                    request.getStaffId(), request.getExamId(), request.getSlotId());
        
        // Check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(request.getStaffId())) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(request.getStaffId()));
        }
        
        Optional<ExamSlot> existingSlot = examSlotRepository.findByExamIdAndSlotId(request.getExamId(), request.getSlotId());
        
        if (!existingSlot.isPresent()) {
            throw new RuntimeException("Slot preference not found");
        }
        
        ExamSlot slot = existingSlot.get();
        
        if (!slot.getPreferredBy().equals(request.getStaffId())) {
            throw new RuntimeException("You can only remove your own preferences");
        }
        
        if (slot.getAllotedTo() != null) {
            throw new RuntimeException("Cannot remove preference for already allotted slot");
        }
        
        examSlotRepository.delete(slot);
        logger.debug("Slot preference removed successfully for staff: {}", request.getStaffId());
        
        return "Slot preference removed successfully";
    }
    
    /*
     Remove slot preference by exam duty ID
     */
    @Transactional
    public String removeSlotPreferenceById(Integer examDutyId, String staffId) {
        logger.debug("Removing slot preference by ID - Staff: {}, ExamDutyId: {}", staffId, examDutyId);
        
        // Check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(staffId)) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(staffId));
        }
        
        Optional<ExamSlot> existingSlot = examSlotRepository.findById(examDutyId);
        
        if (!existingSlot.isPresent()) {
            throw new RuntimeException("Slot preference not found");
        }
        
        ExamSlot slot = existingSlot.get();
        
        if (!slot.getPreferredBy().equals(staffId)) {
            throw new RuntimeException("You can only remove your own preferences");
        }
        
        if (slot.getAllotedTo() != null) {
            throw new RuntimeException("Cannot remove preference for already allotted slot");
        }
        
        examSlotRepository.delete(slot);
        logger.debug("Slot preference removed successfully by ID for staff: {}", staffId);
        
        return "Slot preference removed successfully";
    }
    
    public StaffDutyInfo getStaffDutyInfo(String staffId, Integer academicYear, String term) {
        // Check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(staffId)) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(staffId));
        }
        
        Optional<Staff> staffOpt = staffRepository.findByStaffId(staffId);
        if (!staffOpt.isPresent()) {
            throw new RuntimeException("Staff not found");
        }
        
        Staff staff = staffOpt.get();
        String staffName = staff.getFirstName() + " " + staff.getLastName();
        
        // Get required duties based on cadre
        Optional<StaffDuty> staffDutyOpt = staffDutyRepository.findByStaffIdAndAcademicYearAndTerm(staffId, academicYear, term);
        Integer requiredDuties = staffDutyOpt.map(StaffDuty::getDutyCount).orElse(0);
        
        // Count selected duties
        Long selectedDuties = examSlotRepository.countByPreferredBy(staffId);
        
        return new StaffDutyInfo(staffId, staffName, staff.getAcademicRank(), 
                               requiredDuties, selectedDuties.intValue());
    }
    
    /*
     * Get staff duty info for viewing (allows HOD and CCC to view their info)
     */
    public StaffDutyInfo getStaffDutyInfoForViewing(String staffId, Integer academicYear, String term) {
        Optional<Staff> staffOpt = staffRepository.findByStaffId(staffId);
        if (!staffOpt.isPresent()) {
            throw new RuntimeException("Staff not found");
        }
        
        Staff staff = staffOpt.get();
        String staffName = staff.getFirstName() + " " + staff.getLastName();
        
        // Get required duties based on cadre (0 for HOD and CCC)
        Optional<StaffDuty> staffDutyOpt = staffDutyRepository.findByStaffIdAndAcademicYearAndTerm(staffId, academicYear, term);
        Integer requiredDuties = staffDutyOpt.map(StaffDuty::getDutyCount).orElse(0);
        
        // Count selected duties
        Long selectedDuties = examSlotRepository.countByPreferredBy(staffId);
        
        return new StaffDutyInfo(staffId, staffName, staff.getAcademicRank(), 
                               requiredDuties, selectedDuties.intValue());
    }
    
    public List<SlotResponse> getStaffSelectedSlots(String staffId) {
        // Check if staff is eligible for exam duties
        if (!staffEligibilityService.isEligibleForExamDuty(staffId)) {
            throw new RuntimeException(staffEligibilityService.getIneligibilityReason(staffId));
        }
        
        List<ExamSlot> selectedSlots = examSlotRepository.findByPreferredBy(staffId);
        return convertToSlotResponses(selectedSlots, staffId, true);
    }
    
    /*
     Get staff selected slots for viewing (allows HOD and CCC to view their selections)
     */
    public List<SlotResponse> getStaffSelectedSlotsForViewing(String staffId) {
        List<ExamSlot> selectedSlots = examSlotRepository.findByPreferredBy(staffId);
        return convertToSlotResponses(selectedSlots, staffId, false);
    }
    
    /*
     Helper method to convert ExamSlot entities to SlotResponse DTOs
     */
    private List<SlotResponse> convertToSlotResponses(List<ExamSlot> selectedSlots, String staffId, boolean canRemove) {
        List<SlotResponse> slots = new ArrayList<>();
        
        for (ExamSlot slot : selectedSlots) {
            SlotResponse response = new SlotResponse();
            response.setExamId(slot.getExamId());
            response.setSlotId(slot.getSlotId());
            response.setExamDutyId(slot.getExamDutyId());
            response.setStatus("SELECTED");
            response.setPreferredBy(slot.getPreferredBy());
            response.setCanSelect(canRemove && slot.getAllotedTo() == null);
            
            if (slot.getAllotedTo() != null) {
                response.setReason("Already allotted - cannot remove");
            } else if (canRemove) {
                response.setReason("Click to remove this preference");
            } else {
                response.setReason("View only");
            }
            
            if (slot.getExam() != null) {
                response.setCourseCode(slot.getExam().getCourseCode());
                response.setExamDate(slot.getExam().getExamDate());
                response.setTime(slot.getExam().getTime());
                
                if (slot.getExam().getCourse() != null) {
                    response.setCourseName(slot.getExam().getCourse().getCourseName());
                }
            }
            
            slots.add(response);
        }
        return slots;
    }
}