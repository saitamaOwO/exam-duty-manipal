package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "school", schema = "common")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_seq")
    @SequenceGenerator(name = "school_seq", sequenceName = "common.school_seq", allocationSize = 1)
    @Column(name = "school_code")
    private Integer schoolCode;
    
    @Column(name = "school_name")
    private String schoolName;
    
    public School() {}
    
    public School(String schoolName) {
        this.schoolName = schoolName;
    }
    
    public Integer getSchoolCode() { return schoolCode; }
    public void setSchoolCode(Integer schoolCode) { this.schoolCode = schoolCode; }
    
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
}