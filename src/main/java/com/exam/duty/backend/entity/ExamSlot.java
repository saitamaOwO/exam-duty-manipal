package com.exam.duty.backend.entity;

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
@Table(name = "exam_slot", schema = "examduty")
public class ExamSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_slot_seq")
    @SequenceGenerator(name = "exam_slot_seq", sequenceName = "examduty.exam_slot_seq", allocationSize = 1)
    @Column(name = "exam_duty_id")
    private Integer examDutyId;
    
    @Column(name = "exam_id")
    private Integer examId;
    
    @Column(name = "slot_id")
    private Integer slotId;
    
    @Column(name = "preferred_by")
    private String preferredBy;
    
    @Column(name = "preferred_date")
    private LocalDateTime preferredDate;
    
    @Column(name = "alloted_by")
    private String allotedBy;
    
    @Column(name = "alloted_date")
    private LocalDateTime allotedDate;
    
    @Column(name = "alloted_to")
    private String allotedTo;
    
    @Column(name = "duty_assigned")
    private String dutyAssigned;
    
    @ManyToOne
    @JoinColumn(name = "exam_id", insertable = false, updatable = false)
    private Exam exam;
    
    public ExamSlot() {}
    
    public ExamSlot(Integer examId, Integer slotId, String preferredBy) {
        this.examId = examId;
        this.slotId = slotId;
        this.preferredBy = preferredBy;
        this.preferredDate = LocalDateTime.now();
    }
    
    public Integer getExamDutyId() { return examDutyId; }
    public void setExamDutyId(Integer examDutyId) { this.examDutyId = examDutyId; }
    
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    
    public Integer getSlotId() { return slotId; }
    public void setSlotId(Integer slotId) { this.slotId = slotId; }
    
    public String getPreferredBy() { return preferredBy; }
    public void setPreferredBy(String preferredBy) { this.preferredBy = preferredBy; }
    
    public LocalDateTime getPreferredDate() { return preferredDate; }
    public void setPreferredDate(LocalDateTime preferredDate) { this.preferredDate = preferredDate; }
    
    public String getAllotedBy() { return allotedBy; }
    public void setAllotedBy(String allotedBy) { this.allotedBy = allotedBy; }
    
    public LocalDateTime getAllotedDate() { return allotedDate; }
    public void setAllotedDate(LocalDateTime allotedDate) { this.allotedDate = allotedDate; }
    
    public String getAllotedTo() { return allotedTo; }
    public void setAllotedTo(String allotedTo) { this.allotedTo = allotedTo; }
    
    public String getDutyAssigned() { return dutyAssigned; }
    public void setDutyAssigned(String dutyAssigned) { this.dutyAssigned = dutyAssigned; }
    
    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }
}