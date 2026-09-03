package com.kma.edu.ai_application.config;

import com.kma.edu.ai_application.properties.AiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class AiConfig {

    private final AiProperties aiProperties;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    // Bean dùng cho Trợ lý học vụ — CÓ nhớ hội thoại
    @Bean
    public ChatClient assistantChatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder.defaultSystem(aiProperties.getAssistant().getSystemPrompt())
                .defaultOptions(ChatOptions.builder()
                        .temperature(aiProperties.getAssistant().getTemperature())
                        .maxTokens(aiProperties.getAssistant().getMaxTokens()))
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    // Bean dùng cho sinh trắc nghiệm — KHÔNG cần nhớ hội thoại
    @Bean
    public ChatClient quizChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem(aiProperties.getQuiz().getSystemPrompt())
                .defaultOptions(ChatOptions.builder()
                        .temperature(aiProperties.getQuiz().getTemperature())
                        .maxTokens(aiProperties.getQuiz().getMaxTokens()))
                .build();
    }
}
