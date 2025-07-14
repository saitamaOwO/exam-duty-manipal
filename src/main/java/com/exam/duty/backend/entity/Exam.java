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
@Table(name = "exam", schema = "examduty")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_seq_generator")
    @SequenceGenerator(name = "exam_seq_generator", sequenceName = "examduty.exam_seq", allocationSize = 1)
    @Column(name = "exam_id")
    private Integer examId;

    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "term")
    private String term;

    @Column(name = "semester")
    private String semester;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "exam_time")
    private String examTime;

    @Column(name = "no_of_rooms")
    private Integer noOfRooms;

    @Column(name = "no_of_students")
    private Integer noOfStudents;

    @Column(name = "variation") 
    private Integer variation;

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
    public Exam(Integer examId, Integer academicYear, String programName, String term, String semester, String courseCode,
                LocalDate examDate, String examTime, Integer noOfRooms, Integer noOfStudents, Integer variation,
                Integer noOfInvigilators, Integer noOfRelievers, Integer noOfSquads, Integer otherDepartmentId,
                Integer noOfRequiredResources, String createdBy, LocalDateTime createdDate) {
        this.examId = examId;
        this.academicYear = academicYear;
        this.programName = programName;
        this.term = term;
        this.semester = semester;
        this.courseCode = courseCode;
        this.examDate = examDate;
        this.examTime = examTime;
        this.noOfRooms = noOfRooms;
        this.noOfStudents = noOfStudents;
        this.variation = variation;
        this.noOfInvigilators = noOfInvigilators;
        this.noOfRelievers = noOfRelievers;
        this.noOfSquads = noOfSquads;
        this.otherDepartmentId = otherDepartmentId;
        this.noOfRequiredResources = noOfRequiredResources;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public String getExamTime() { return examTime; }
    public void setExamTime(String examTime) { this.examTime = examTime; }

    public Integer getNoOfRooms() { return noOfRooms; }
    public void setNoOfRooms(Integer noOfRooms) { this.noOfRooms = noOfRooms; }

    public Integer getNoOfStudents() { return noOfStudents; }
    public void setNoOfStudents(Integer noOfStudents) { this.noOfStudents = noOfStudents; }

    public Integer getVariation() { return variation; }
    public void setVariation(Integer variation) { this.variation = variation; }

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