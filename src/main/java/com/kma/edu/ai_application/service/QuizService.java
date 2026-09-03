package com.kma.edu.ai_application.service;

import com.kma.edu.ai_application.dto.QuizList;
import com.kma.edu.ai_application.enums.QuestionLevel;
import com.kma.edu.ai_application.exception.AppException;
import com.kma.edu.ai_application.exception.ErrorCode;
import com.kma.edu.ai_application.properties.AiProperties;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;

@Service
@Slf4j
public class QuizService {
    private final ChatClient chatClient;
    private final AiProperties aiProperties;

    public QuizService(@Qualifier("quizChatClient") ChatClient chatClient, AiProperties aiProperties) {
        this.chatClient = chatClient;
        this.aiProperties = aiProperties;
    }

    @Retryable(
            retryFor = {AppException.class},
            backoff = @Backoff(delay = 1000, multiplier = 2) // 1s, 2s, 4s...
            )
    public QuizList generate(String topic, int count, QuestionLevel level) {

        try {
            PromptTemplate template = new PromptTemplate(aiProperties.getQuiz().getSystemPrompt());
            String renderedPrompt = template.render(Map.of("count", count, "topic", topic, "level", level.getLevel()));

            return chatClient
                    .prompt()
                    .system(renderedPrompt)
                    .user("Bắt đầu tạo câu hỏi.")
                    .call()
                    .entity(QuizList.class);

        } catch (Exception exception) {

            // exception thật bị wrapped, cần lấy truy xuống cause lấy exception gốc
            Throwable rootCause = getRootCause(exception);

            // lỗi JSON schema
            if (rootCause instanceof JacksonException) {
                log.warn("AI output invalid | type=JSON | action=retry | cause={}", rootCause.getMessage());
                throw new AppException(ErrorCode.AI_INVALID_OUTPUT_FORMAT);
            }

            // JSON fields Validation
            if (rootCause instanceof IllegalArgumentException) {
                log.warn("AI output invalid | type=validation | action=retry | cause={}", rootCause.getMessage());
                throw new AppException(ErrorCode.AI_INVALID_OUTPUT_FORMAT);
            }

            // fallback
            log.error("AI generation failed | action=fallback | cause={}", exception.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Recover // fallback khi retry hết maxAttempts, ném exception cho AiExceptionHanlder xử lý
    public QuizList fallback(AppException exception, String topic, int count, QuestionLevel level) {
        log.error("AI generation failed after max retries | fallback=true | cause={}", exception.getMessage());
        throw new AppException(ErrorCode.AI_SERVICE_UNAVAILABLE);
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }
}
