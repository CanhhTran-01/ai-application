package com.kma.edu.ai_application.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "student_grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, length = 20)
    private String studentCode;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Column(name = "score", nullable = false, precision = 4, scale = 2)
    private BigDecimal score;
}
