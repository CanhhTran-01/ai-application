package com.kma.edu.ai_application.repository;

import com.kma.edu.ai_application.entity.Grade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentCode(String studentCode);

    List<Grade> findByStudentCodeAndCourseNameContainingIgnoreCase(String studentCode, String courseName);
}
