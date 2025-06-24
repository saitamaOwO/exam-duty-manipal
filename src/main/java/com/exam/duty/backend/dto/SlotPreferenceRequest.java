package com.exam.duty.backend.dto;

import jakarta.validation.constraints.NotNull;

public class SlotPreferenceRequest {
    @NotNull
    private Integer examId;
    
    @NotNull
    private Integer slotId;
    
    @NotNull
    private String staffId;
    
    public SlotPreferenceRequest() {}
    
    public SlotPreferenceRequest(Integer examId, Integer slotId, String staffId) {
        this.examId = examId;
        this.slotId = slotId;
        this.staffId = staffId;
    }
    
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    
    public Integer getSlotId() { return slotId; }
    public void setSlotId(Integer slotId) { this.slotId = slotId; }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
}