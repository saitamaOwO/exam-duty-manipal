package com.exam.duty.backend.dto;

import jakarta.validation.constraints.NotNull;

public class SecureSlotPreferenceRequest {
    @NotNull
    private Integer examId;
    
    @NotNull
    private Integer slotId;
    
    public SecureSlotPreferenceRequest() {}
    
    public SecureSlotPreferenceRequest(Integer examId, Integer slotId) {
        this.examId = examId;
        this.slotId = slotId;
    }
    
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    
    public Integer getSlotId() { return slotId; }
    public void setSlotId(Integer slotId) { this.slotId = slotId; }
}
