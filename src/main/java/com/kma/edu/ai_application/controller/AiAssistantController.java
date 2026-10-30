package com.kma.edu.ai_application.controller;

import com.kma.edu.ai_application.dto.ApiResponse;
import com.kma.edu.ai_application.service.AssistantService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/assistant")
public class AiAssistantController {

    private final AssistantService assistantService;

    public AiAssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/chat")
    public ApiResponse<String> chat(
            @RequestParam String message, @RequestParam(defaultValue = "default-session") String conversationId) {

        String studentCodeInJwtClaims = "SV001";
        return ApiResponse.success(assistantService.chat(message, conversationId, studentCodeInJwtClaims));
    }
}
