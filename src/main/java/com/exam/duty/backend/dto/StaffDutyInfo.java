package com.exam.duty.backend.dto;

import com.exam.duty.backend.entity.AcademicRank;

public class StaffDutyInfo {
    private String staffId;
    private String staffName;
    private AcademicRank academicRank;
    private Integer requiredDuties;
    private Integer selectedDuties;
    private Integer remainingDuties;
    
    public StaffDutyInfo() {}
    
    public StaffDutyInfo(String staffId, String staffName, AcademicRank academicRank, 
                        Integer requiredDuties, Integer selectedDuties) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.academicRank = academicRank;
        this.requiredDuties = requiredDuties;
        this.selectedDuties = selectedDuties;
        this.remainingDuties = requiredDuties - selectedDuties;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    
    public AcademicRank getAcademicRank() { return academicRank; }
    public void setAcademicRank(AcademicRank academicRank) { this.academicRank = academicRank; }
    
    public Integer getRequiredDuties() { return requiredDuties; }
    public void setRequiredDuties(Integer requiredDuties) { this.requiredDuties = requiredDuties; }
    
    public Integer getSelectedDuties() { return selectedDuties; }
    public void setSelectedDuties(Integer selectedDuties) { this.selectedDuties = selectedDuties; }
    
    public Integer getRemainingDuties() { return remainingDuties; }
    public void setRemainingDuties(Integer remainingDuties) { this.remainingDuties = remainingDuties; }
}