package com.exam.duty.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class StaffCourseId implements Serializable {
    private String staffId;
    private Integer courseId;

    public StaffCourseId() {}

    public StaffCourseId(String staffId, Integer courseId) {
        this.staffId = staffId;
        this.courseId = courseId;
    }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffCourseId that = (StaffCourseId) o;
        return Objects.equals(staffId, that.staffId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffId, courseId);
    }
}
