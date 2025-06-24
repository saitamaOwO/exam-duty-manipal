package com.exam.duty.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.duty.backend.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    
    Optional<Staff> findByStaffId(String staffId);
    
    List<Staff> findByStaffType(String staffType);
    
    @Query("SELECT s FROM Staff s WHERE s.staffType = 'TEACHING'")
    List<Staff> findAllTeachingStaff();
    
    @Query("SELECT s FROM Staff s JOIN s.courses c WHERE c.courseCode = :courseCode")
    List<Staff> findStaffByCourseCode(@Param("courseCode") String courseCode);
    
    @Query("SELECT r.roleName FROM Staff s " +
           "JOIN StaffRole sr ON s.staffId = sr.staffId " +
           "JOIN Role r ON sr.roleId = r.roleId " +
           "WHERE s.staffId = :staffId")
    List<String> findStaffRoles(@Param("staffId") String staffId);
    
    @Query("SELECT DISTINCT s FROM Staff s " +
           "LEFT JOIN StaffRole sr ON s.staffId = sr.staffId " +
           "LEFT JOIN Role r ON sr.roleId = r.roleId " +
           "WHERE s.staffType = 'TEACHING' " +
           "AND (r.roleName IS NULL OR (r.roleName != 'HOD' AND r.roleName != 'CLASS_COMMITTEE_CHAIRPERSON'))")
    List<Staff> findEligibleStaffForExamDuty();
    
    @Query("SELECT CASE WHEN COUNT(sr) > 0 THEN true ELSE false END " +
           "FROM StaffRole sr JOIN Role r ON sr.roleId = r.roleId " +
           "WHERE sr.staffId = :staffId AND r.roleName = :roleName")
    boolean hasRole(@Param("staffId") String staffId, @Param("roleName") String roleName);
}