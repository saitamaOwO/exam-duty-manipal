package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_course", schema = "common")
@IdClass(StaffCourseId.class)
public class StaffCourse {
    @Id
    @Column(name = "staff_id")
    private String staffId;
    
    @Id
    @Column(name = "course_id")
    private Integer courseId;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
    
    public StaffCourse() {}
    
    public StaffCourse(String staffId, Integer courseId) {
        this.staffId = staffId;
        this.courseId = courseId;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}