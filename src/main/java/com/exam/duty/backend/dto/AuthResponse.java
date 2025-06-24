package com.exam.duty.backend.dto;

import java.util.List;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String staffId;
    private String staffName;
    private List<String> roles;
    
    public AuthResponse() {}
    
    public AuthResponse(String token, String staffId, String staffName, List<String> roles) {
        this.token = token;
        this.staffId = staffId;
        this.staffName = staffName;
        this.roles = roles;
    }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}