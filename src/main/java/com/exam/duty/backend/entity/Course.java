package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "course", schema = "common")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "common.course_seq", allocationSize = 1)
    @Column(name = "course_id")
    private Integer courseId;
    
    @Column(name = "course_code", unique = true)
    private String courseCode;
    
    @Column(name = "course_type")
    private String courseType;
    
    @Column(name = "course_name")
    private String courseName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "credit")
    private Integer credit;
    
    @Column(name = "program")
    private String program;
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "school_code")
    private Integer schoolCode;
    
    @Column(name = "specialization_code")
    private Integer specializationCode;
    
    @Column(name = "status")
    private String status;
    
    public Course() {}
    
    public Course(String courseCode, String courseType, String courseName, String semester) {
        this.courseCode = courseCode;
        this.courseType = courseType;
        this.courseName = courseName;
        this.semester = semester;
    }
    
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }
    
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public Integer getSchoolCode() { return schoolCode; }
    public void setSchoolCode(Integer schoolCode) { this.schoolCode = schoolCode; }
    
    public Integer getSpecializationCode() { return specializationCode; }
    public void setSpecializationCode(Integer specializationCode) { this.specializationCode = specializationCode; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}