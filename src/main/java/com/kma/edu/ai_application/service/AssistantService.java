package com.kma.edu.ai_application.service;

import com.kma.edu.ai_application.tools.GradeTools;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {
    private final ChatClient chatClient;
    private final GradeTools gradeTools;

    public AssistantService(@Qualifier("assistantChatClient") ChatClient chatClient, GradeTools gradeTools) {
        this.chatClient = chatClient;
        this.gradeTools = gradeTools;
    }

    public String chat(String message, String conversationId, String studentCode) {

        // gắn lịch sử hội thoại với sinh viên, tránh người khác đoán conversationId để đọc ké
        String memoryId = studentCode + ":" + conversationId;

        return chatClient
                .prompt()
                .user(message)
                .tools(gradeTools)
                .toolContext(Map.of(GradeTools.STUDENT_CODE_KEY, studentCode))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, memoryId))
                .call()
                .content();
    }
}
