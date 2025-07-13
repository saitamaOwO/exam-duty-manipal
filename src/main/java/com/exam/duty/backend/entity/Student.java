package com.exam.duty.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student", schema = "common")
public class Student {
    @Id
    @Column(name = "registration_id")
    private Integer registrationId;
    
    @Column(name = "student_type")
    private String studentType;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "learner_email")
    private String learnerEmail;
    
    @Column(name = "personal_email")
    private String personalEmail;
    
    @Column(name = "mobile")
    private Long mobile;
    
    @Column(name = "specialization_code")
    private Integer specializationCode;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "specialization_code", insertable = false, updatable = false)
    private Specialization specialization;
    
    public Student() {}
    
    public Student(Integer registrationId, String studentType, String firstName, String lastName) {
        this.registrationId = registrationId;
        this.studentType = studentType;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }
    
    public String getStudentType() { return studentType; }
    public void setStudentType(String studentType) { this.studentType = studentType; }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getLearnerEmail() { return learnerEmail; }
    public void setLearnerEmail(String learnerEmail) { this.learnerEmail = learnerEmail; }
    
    public String getPersonalEmail() { return personalEmail; }
    public void setPersonalEmail(String personalEmail) { this.personalEmail = personalEmail; }
    
    public Long getMobile() { return mobile; }
    public void setMobile(Long mobile) { this.mobile = mobile; }
    
    public Integer getSpecializationCode() { return specializationCode; }
    public void setSpecializationCode(Integer specializationCode) { this.specializationCode = specializationCode; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Specialization getSpecialization() { return specialization; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }
}