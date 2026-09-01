package com.kma.edu.ai_application.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiPingController {

    private final ChatClient chatClient;

    public AiPingController(@Qualifier("assistantChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ping")
    public String ping(
            @RequestParam String question, @RequestParam(defaultValue = "default-session") String conversationId) {

        return chatClient
                .prompt()
                .user(question)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
