package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "STAFF_ROLE")
@IdClass(StaffRoleId.class)
public class StaffRole {
    @Id
    @Column(name = "staff_id")
    private String staffId;
    
    @Id
    @Column(name = "role_id")
    private Integer roleId;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;
    
    public StaffRole() {}
    
    public StaffRole(String staffId, Integer roleId) {
        this.staffId = staffId;
        this.roleId = roleId;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}