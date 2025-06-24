package com.exam.duty.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.duty.backend.entity.StaffRole;
import com.exam.duty.backend.entity.StaffRoleId;

@Repository
public interface StaffRoleRepository extends JpaRepository<StaffRole, StaffRoleId> {
    
    List<StaffRole> findByStaffId(String staffId);
    
    @Query("SELECT r.roleName FROM StaffRole sr JOIN Role r ON sr.roleId = r.roleId WHERE sr.staffId = :staffId")
    List<String> findRoleNamesByStaffId(@Param("staffId") String staffId);
    
    boolean existsByStaffIdAndRoleId(String staffId, Integer roleId);
    
    void deleteByStaffIdAndRoleId(String staffId, Integer roleId);
}