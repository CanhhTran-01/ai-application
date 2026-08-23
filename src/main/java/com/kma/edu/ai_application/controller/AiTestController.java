package com.kma.edu.ai_application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiTestController {

    private final ChatClient chatClient;

    @GetMapping("/ping")
    public String ping(@RequestParam String question) {
        return chatClient.prompt().user(question).call().content();
    }
}
