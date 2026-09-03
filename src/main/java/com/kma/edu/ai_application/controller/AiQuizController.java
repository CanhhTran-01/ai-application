package com.kma.edu.ai_application.controller;

import com.kma.edu.ai_application.dto.ApiResponse;
import com.kma.edu.ai_application.dto.QuizList;
import com.kma.edu.ai_application.enums.QuestionLevel;
import com.kma.edu.ai_application.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiQuizController {

    private final QuizService quizService;

    @GetMapping("/quiz-generate")
    public ResponseEntity<ApiResponse<QuizList>> generate(
            @RequestParam String topic,
            @RequestParam(defaultValue = "3") int count,
            @RequestParam(defaultValue = "RECOGNITION") QuestionLevel level) {

        QuizList result = quizService.generate(topic, count, level);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
