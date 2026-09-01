package com.kma.edu.ai_application.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiQuizController {

    private final ChatClient chatClient;

    public AiQuizController(@Qualifier("quizChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/quiz-generate")
    public String generate(@RequestParam String topic, @RequestParam(defaultValue = "3") int count) {

        String userPrompt = ("Tạo %d câu hỏi trắc nghiệm mới về chủ đề: %s").formatted(count, topic);

        return chatClient.prompt().user(userPrompt).call().content();
    }
}
