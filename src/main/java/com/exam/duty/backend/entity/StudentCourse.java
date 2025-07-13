package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_course", schema = "common")
@IdClass(StudentCourseId.class)
public class StudentCourse {
    @Id
    @Column(name = "registration_id")
    private Integer registrationId;
    
    @Id
    @Column(name = "course_id")
    private Integer courseId;
    
    @ManyToOne
    @JoinColumn(name = "registration_id", insertable = false, updatable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
    
    public StudentCourse() {}
    
    public StudentCourse(Integer registrationId, Integer courseId) {
        this.registrationId = registrationId;
        this.courseId = courseId;
    }
    
    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }
    
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}