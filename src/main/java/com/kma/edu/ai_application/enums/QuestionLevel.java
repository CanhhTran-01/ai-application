package com.kma.edu.ai_application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionLevel {
    RECOGNITION("Nhận biết"),
    COMPREHENSION("Thông hiểu"),
    APPLICATION("Vận dụng"),
    HIGH_APPLICATION("Vận dụng cao");

    private final String level;
}
