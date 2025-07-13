package com.exam.duty.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam", schema = "examduty")
public class Exam {
    @Id
    @Column(name = "exam_id")
    private Integer examId;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "term")
    private String term;

    @Column(name = "academic_year")
    private Integer academicYear;

    @Column(name = "exam_type")
    private String examType;

    @Column(name = "exam_venue")
    private String examVenue;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    public Exam() {}

    public Exam(Integer examId, Integer courseId, LocalDate examDate, LocalTime startTime, Integer duration, String term, Integer academicYear, String examType) {
        this.examId = examId;
        this.courseId = courseId;
        this.examDate = examDate;
        this.startTime = startTime;
        this.duration = duration;
        this.term = term;
        this.academicYear = academicYear;
        this.examType = examType;
    }

    // Getters and Setters
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public String getExamVenue() { return examVenue; }
    public void setExamVenue(String examVenue) { this.examVenue = examVenue; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public int getNoOfInvigilators() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNoOfRelievers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNoOfSquads() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}