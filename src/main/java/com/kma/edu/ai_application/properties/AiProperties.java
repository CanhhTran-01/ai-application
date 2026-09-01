package com.kma.edu.ai_application.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kma.ai")
public class AiProperties {

    private Assistant assistant = new Assistant();
    private Quiz quiz = new Quiz();

    @Getter
    @Setter
    public static class Assistant {
        private String systemPrompt;
        private double temperature;
        private int maxTokens;
    }

    @Getter
    @Setter
    public static class Quiz {
        private String systemPrompt;
        private double temperature;
        private int maxTokens;
    }
}
