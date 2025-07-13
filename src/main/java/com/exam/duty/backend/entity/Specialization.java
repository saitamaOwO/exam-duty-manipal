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
@Table(name = "specialization", schema = "common")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialization_seq")
    @SequenceGenerator(name = "specialization_seq", sequenceName = "common.specialization_seq", allocationSize = 1)
    @Column(name = "specialization_code")
    private Integer specializationCode;
    
    @Column(name = "specialization_name")
    private String specializationName;
    
    @Column(name = "school_code")
    private Integer schoolCode;
    
    @ManyToOne
    @JoinColumn(name = "school_code", insertable = false, updatable = false)
    private School school;
    
    public Specialization() {}
    
    public Specialization(String specializationName, Integer schoolCode) {
        this.specializationName = specializationName;
        this.schoolCode = schoolCode;
    }
    
    public Integer getSpecializationCode() { return specializationCode; }
    public void setSpecializationCode(Integer specializationCode) { this.specializationCode = specializationCode; }
    
    public String getSpecializationName() { return specializationName; }
    public void setSpecializationName(String specializationName) { this.specializationName = specializationName; }
    
    public Integer getSchoolCode() { return schoolCode; }
    public void setSchoolCode(Integer schoolCode) { this.schoolCode = schoolCode; }
    
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
}