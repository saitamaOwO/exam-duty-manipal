package com.exam.duty.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.duty.backend.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    
    Optional<Course> findByCourseCode(String courseCode);
}