package com.exam.duty.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.duty.backend.entity.StaffDuty;

@Repository
public interface StaffDutyRepository extends JpaRepository<StaffDuty, Integer> {
    
    Optional<StaffDuty> findByStaffIdAndAcademicYearAndTerm(String staffId, Integer academicYear, String term);
    
    List<StaffDuty> findByAcademicYearAndTerm(Integer academicYear, String term);
}