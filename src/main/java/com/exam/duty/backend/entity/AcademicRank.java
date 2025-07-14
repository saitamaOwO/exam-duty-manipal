package com.exam.duty.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "academic_rank", schema = "common")
public class AcademicRank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academic_rank_seq")
    @SequenceGenerator(name = "academic_rank_seq", sequenceName = "common.academic_rank_seq", allocationSize = 1)
    @Column(name = "academic_rank_id")
    private Integer academicRankId;

    @Column(name = "name")
    private String name;

    @Column(name = "cadre_id")
    private Integer cadreId;

    @Column(name = "examduty_base_workload")
    private Integer examdutyBaseWorkload;

    // Constructors
    public AcademicRank() {}

    public AcademicRank(String name, Integer cadreId, Integer examdutyBaseWorkload) {
        this.name = name;
        this.cadreId = cadreId;
        this.examdutyBaseWorkload = examdutyBaseWorkload;
    }

    // Getters and Setters
    public Integer getAcademicRankId() { return academicRankId; }
    public void setAcademicRankId(Integer academicRankId) { this.academicRankId = academicRankId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getCadreId() { return cadreId; }
    public void setCadreId(Integer cadreId) { this.cadreId = cadreId; }

    public Integer getExamdutyBaseWorkload() { return examdutyBaseWorkload; }
    public void setExamdutyBaseWorkload(Integer examdutyBaseWorkload) { this.examdutyBaseWorkload = examdutyBaseWorkload; }

    public String toLowerCase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toLowerCase'");
    }
}