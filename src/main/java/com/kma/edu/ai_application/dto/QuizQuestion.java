package com.kma.edu.ai_application.dto;

import com.kma.edu.ai_application.enums.QuestionLevel;
import java.util.HashSet;
import java.util.List;

public record QuizQuestion(
        String question,
        List<String> options, // đúng 4 phần tử
        int correctOptionIndex, // index: 0-3
        String explanation,
        QuestionLevel level) {

    public QuizQuestion {

        // câu hỏi không được null
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung câu hỏi không được để trống.");
        }

        // danh sách đáp án phải có chính xác 4 phần tử
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException(
                    "Câu hỏi phải có chính xác 4 đáp án (hiện có " + (options == null ? 0 : options.size()) + ").");
        }

        // đáp án không bị trùng lặp
        if (new HashSet<>(options).size() < 4) {
            throw new IllegalArgumentException("Các đáp án trong câu hỏi không được trùng lặp nội dung.");
        }

        // index đáp án đúng phải từ 0 đến 3
        if (correctOptionIndex < 0 || correctOptionIndex > 3) {
            throw new IllegalArgumentException("Chỉ số đáp án đúng (correctOptionIndex) phải nằm trong khoảng 0-3. "
                    + "Giá trị nhận được: " + correctOptionIndex);
        }

        // phần giải thích không được null
        if (explanation == null || explanation.trim().isEmpty()) {
            throw new IllegalArgumentException("Phần giải thích đáp án không được để trống.");
        }

        //  level không được null
        if (level == null) {
            throw new IllegalArgumentException("Mức độ câu hỏi (level) không được để null.");
        }
    }
}
