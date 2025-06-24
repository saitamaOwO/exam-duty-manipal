package com.exam.duty.backend.repository;

import com.exam.duty.backend.entity.ExamSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamSlotRepository extends JpaRepository<ExamSlot, Integer> {
    
    List<ExamSlot> findByExamId(Integer examId);
    
    List<ExamSlot> findByPreferredBy(String staffId);
    
    Optional<ExamSlot> findByExamIdAndSlotId(Integer examId, Integer slotId);
    
    @Query("SELECT es FROM ExamSlot es JOIN es.exam e WHERE es.preferredBy = :staffId " +
           "AND e.examDate = :examDate")
    List<ExamSlot> findByStaffIdAndExamDate(@Param("staffId") String staffId, 
                                           @Param("examDate") LocalDate examDate);
    
    @Query("SELECT COUNT(es) FROM ExamSlot es WHERE es.preferredBy = :staffId")
    Long countByPreferredBy(@Param("staffId") String staffId);
    
    @Query("SELECT es FROM ExamSlot es JOIN es.exam e WHERE e.academicYear = :academicYear " +
           "AND e.term = :term ORDER BY e.examDate, e.time")
    List<ExamSlot> findAllSlotsForTerm(@Param("academicYear") Integer academicYear, 
                                      @Param("term") String term);
}