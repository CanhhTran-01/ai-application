package com.kma.edu.ai_application.controller;

import com.kma.edu.ai_application.enums.QuestionLevel;
import com.kma.edu.ai_application.properties.AiProperties;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiQuizController {

    private final ChatClient chatClient;
    private final AiProperties aiProperties;

    public AiQuizController(@Qualifier("quizChatClient") ChatClient chatClient, AiProperties aiProperties) {
        this.chatClient = chatClient;
        this.aiProperties = aiProperties;
    }

    @GetMapping("/quiz-generate")
    public String generate(
            @RequestParam String topic,
            @RequestParam(defaultValue = "3") int count,
            @RequestParam(defaultValue = "RECOGNITION") QuestionLevel level) {

        PromptTemplate template = new PromptTemplate(aiProperties.getQuiz().getSystemPrompt());
        String renderedPrompt = template.render(Map.of("count", count, "topic", topic, "level", level.getLevel()));

        return chatClient
                .prompt()
                .system(renderedPrompt)
                .user("Bắt đầu tạo câu hỏi.")
                .call()
                .content();
    }
}
