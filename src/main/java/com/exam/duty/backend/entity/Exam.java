package com.exam.duty.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "EXAM")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_seq")
    @SequenceGenerator(name = "exam_seq", sequenceName = "exam_seq", allocationSize = 1)
    @Column(name = "exam_id")
    private Integer examId;
    
    @Column(name = "academic_year")
    private Integer academicYear;
    
    @Column(name = "term")
    private String term;
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "course_code")
    private String courseCode;
    
    @Column(name = "exam_date")
    private LocalDate examDate;
    
    @Column(name = "time")
    private String time;
    
    @Column(name = "no_of_invigilators")
    private Integer noOfInvigilators;
    
    @Column(name = "no_of_relievers")
    private Integer noOfRelievers;
    
    @Column(name = "no_of_squads")
    private Integer noOfSquads;
    
    @Column(name = "other_department_id")
    private Integer otherDepartmentId;
    
    @Column(name = "no_of_required_resources")
    private Integer noOfRequiredResources;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @ManyToOne
    @JoinColumn(name = "course_code", referencedColumnName = "course_code", insertable = false, updatable = false)
    private Course course;
    public Exam() {}

    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    
    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }
    
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public Integer getNoOfInvigilators() { return noOfInvigilators; }
    public void setNoOfInvigilators(Integer noOfInvigilators) { this.noOfInvigilators = noOfInvigilators; }
    
    public Integer getNoOfRelievers() { return noOfRelievers; }
    public void setNoOfRelievers(Integer noOfRelievers) { this.noOfRelievers = noOfRelievers; }
    
    public Integer getNoOfSquads() { return noOfSquads; }
    public void setNoOfSquads(Integer noOfSquads) { this.noOfSquads = noOfSquads; }
    
    public Integer getOtherDepartmentId() { return otherDepartmentId; }
    public void setOtherDepartmentId(Integer otherDepartmentId) { this.otherDepartmentId = otherDepartmentId; }
    
    public Integer getNoOfRequiredResources() { return noOfRequiredResources; }
    public void setNoOfRequiredResources(Integer noOfRequiredResources) { this.noOfRequiredResources = noOfRequiredResources; }
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}