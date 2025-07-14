package com.exam.duty.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.duty.backend.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    List<Exam> findByAcademicYearAndTerm(Integer academicYear, String term);

    List<Exam> findByExamDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.course WHERE e.academicYear = :academicYear AND e.term = :term ORDER BY e.examDate, e.examTime")
    List<Exam> findExamsForSlotGeneration(@Param("academicYear") Integer academicYear,
                                         @Param("term") String term);
}
