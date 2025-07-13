package com.exam.duty.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class StudentCourseId implements Serializable {
    private Integer registrationId;
    private Integer courseId;
    
    public StudentCourseId() {}
    
    public StudentCourseId(Integer registrationId, Integer courseId) {
        this.registrationId = registrationId;
        this.courseId = courseId;
    }
    
    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }
    
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseId that = (StudentCourseId) o;
        return Objects.equals(registrationId, that.registrationId) && Objects.equals(courseId, that.courseId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(registrationId, courseId);
    }
}