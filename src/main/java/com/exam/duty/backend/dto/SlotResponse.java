package com.exam.duty.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotResponse {
    private Integer examId;
    private Integer slotId;
    private Integer examDutyId;
    private String courseCode;
    private String courseName;
    private LocalDate examDate;
    private LocalTime time;
    private String status;
    private String preferredBy;
    private boolean canSelect;
    private String reason;
    
    public SlotResponse() {}
    
    public SlotResponse(Integer examId, Integer slotId, String courseCode, String courseName, 
                      LocalDate examDate, LocalTime time, String status) {
        this.examId = examId;
        this.slotId = slotId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.examDate = examDate;
        this.time = time;
        this.status = status;
    }
    
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    
    public Integer getSlotId() { return slotId; }
    public void setSlotId(Integer slotId) { this.slotId = slotId; }
    
    public Integer getExamDutyId() { return examDutyId; }
    public void setExamDutyId(Integer examDutyId) { this.examDutyId = examDutyId; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPreferredBy() { return preferredBy; }
    public void setPreferredBy(String preferredBy) { this.preferredBy = preferredBy; }
    
    public boolean isCanSelect() { return canSelect; }
    public void setCanSelect(boolean canSelect) { this.canSelect = canSelect; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}