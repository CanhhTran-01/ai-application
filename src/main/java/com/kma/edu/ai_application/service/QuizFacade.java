package com.kma.edu.ai_application.service;

import com.kma.edu.ai_application.dto.QuizList;
import com.kma.edu.ai_application.enums.QuestionLevel;
import com.kma.edu.ai_application.exception.AppException;
import com.kma.edu.ai_application.exception.ErrorCode;
import com.kma.edu.ai_application.properties.AiProperties;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuizFacade {
    private final VectorStore vectorStore;
    private final QuizService quizService;
    private final AiProperties aiProperties;

    public QuizFacade(VectorStore vectorStore, QuizService quizService, AiProperties aiProperties) {
        this.vectorStore = vectorStore;
        this.quizService = quizService;
        this.aiProperties = aiProperties;
    }

    public QuizList generate(String topic, int count, QuestionLevel level) {
        AiProperties.Quiz quiz = aiProperties.getQuiz();

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(topic)
                .topK(quiz.getTopK())
                .similarityThreshold(quiz.getSimilarityThreshold())
                .build());

        if (docs.isEmpty()) {
            throw new AppException(ErrorCode.QUIZ_SOURCE_NOT_FOUND);
        }

        String context = docs.stream().map(Document::getText).collect(Collectors.joining("\n\n---\n\n"));
        log.info("Quiz context: {} chunk(s) cho chủ đề '{}'", docs.size(), topic);

        return quizService.generate(topic, count, level, context);
    }
}
