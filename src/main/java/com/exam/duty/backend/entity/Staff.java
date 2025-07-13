package com.exam.duty.backend.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff", schema = "common")
public class Staff {
    @Id
    @Column(name = "staff_id")
    private String staffId;
    
    @Column(name = "staff_type")
    private String staffType;
    
    @Column(name = "academic_rank_id")
    private Integer academicRankId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "mobile")
    private Long mobile;
    
    @Column(name = "cabin_location")
    private String cabinLocation;
    
    @Column(name = "examduty_workload")
    private Integer examdutyWorkload;
    
    @Column(name = "school_id")
    private Integer schoolId;
    
    @Column(name = "department_id")
    private Integer departmentId;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "academic_rank_id", insertable = false, updatable = false)
    private AcademicRank academicRank;
    
    @ManyToOne
    @JoinColumn(name = "school_id", insertable = false, updatable = false)
    private School school;
    
    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "staff_course",
        schema = "common",
        joinColumns = @JoinColumn(name = "staff_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
    
    @OneToMany(mappedBy = "staff", fetch = FetchType.EAGER)
    private Set<StaffRole> staffRoles;
    
    public Staff() {}
    
    public Staff(String staffId, String staffType, String firstName, String lastName, String email) {
        this.staffId = staffId;
        this.staffType = staffType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getStaffType() { return staffType; }
    public void setStaffType(String staffType) { this.staffType = staffType; }
    
    public Integer getAcademicRankId() { return academicRankId; }
    public void setAcademicRankId(Integer academicRankId) { this.academicRankId = academicRankId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Long getMobile() { return mobile; }
    public void setMobile(Long mobile) { this.mobile = mobile; }
    
    public String getCabinLocation() { return cabinLocation; }
    public void setCabinLocation(String cabinLocation) { this.cabinLocation = cabinLocation; }
    
    public Integer getExamdutyWorkload() { return examdutyWorkload; }
    public void setExamdutyWorkload(Integer examdutyWorkload) { this.examdutyWorkload = examdutyWorkload; }
    
    public Integer getSchoolId() { return schoolId; }
    public void setSchoolId(Integer schoolId) { this.schoolId = schoolId; }
    
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public AcademicRank getAcademicRank() { return academicRank; }
    public void setAcademicRank(AcademicRank academicRank) { this.academicRank = academicRank; }
    
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public Set<Course> getCourses() { return courses; }
    public void setCourses(Set<Course> courses) { this.courses = courses; }
    
    public Set<StaffRole> getStaffRoles() { return staffRoles; }
    public void setStaffRoles(Set<StaffRole> staffRoles) { this.staffRoles = staffRoles; }
}