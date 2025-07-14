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
@Table(name = "staff_duty", schema = "examduty")
public class StaffDuty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_duty_seq")
    @SequenceGenerator(name = "staff_duty_seq", sequenceName = "examduty.staff_duty_seq", allocationSize = 1)
    @Column(name = "staff_duty_id")
    private Integer staffDutyId;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "academic_year")
    private Integer academicYear;

    @Column(name = "term")
    private String term;

    @Column(name = "duty_count")
    private Integer dutyCount;

    @ManyToOne
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;

    public StaffDuty() {}

    public StaffDuty(String staffId, Integer academicYear, String term, Integer dutyCount) {
        this.staffId = staffId;
        this.academicYear = academicYear;
        this.term = term;
        this.dutyCount = dutyCount;
    }

    public Integer getStaffDutyId() { return staffDutyId; }
    public void setStaffDutyId(Integer staffDutyId) { this.staffDutyId = staffDutyId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Integer getDutyCount() { return dutyCount; }
    public void setDutyCount(Integer dutyCount) { this.dutyCount = dutyCount; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
}
