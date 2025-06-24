package com.exam.duty.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank
    private String staffId;
    
    @NotBlank
    private String password;
    
    public AuthRequest() {}
    
    public AuthRequest(String staffId, String password) {
        this.staffId = staffId;
        this.password = password;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
