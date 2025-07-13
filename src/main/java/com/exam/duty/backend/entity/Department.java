package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "department", schema = "common")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "common.department_seq", allocationSize = 1)
    @Column(name = "department_id")
    private Integer departmentId;
    
    @Column(name = "department_name")
    private String departmentName;
    
    @Column(name = "school_code")
    private Integer schoolCode;
    
    @ManyToOne
    @JoinColumn(name = "school_code", insertable = false, updatable = false)
    private School school;
    
    public Department() {}
    
    public Department(String departmentName, Integer schoolCode) {
        this.departmentName = departmentName;
        this.schoolCode = schoolCode;
    }
    
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public Integer getSchoolCode() { return schoolCode; }
    public void setSchoolCode(Integer schoolCode) { this.schoolCode = schoolCode; }
    
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
}