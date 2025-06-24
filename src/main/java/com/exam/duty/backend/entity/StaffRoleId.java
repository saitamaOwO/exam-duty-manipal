package com.exam.duty.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class StaffRoleId implements Serializable {
    private String staffId;
    private Integer roleId;
    
    public StaffRoleId() {}
    
    public StaffRoleId(String staffId, Integer roleId) {
        this.staffId = staffId;
        this.roleId = roleId;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    
    //equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffRoleId that = (StaffRoleId) o;
        return Objects.equals(staffId, that.staffId) && Objects.equals(roleId, that.roleId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(staffId, roleId);
    }
}