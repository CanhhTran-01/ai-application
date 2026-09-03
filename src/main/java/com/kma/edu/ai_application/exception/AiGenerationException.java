package com.kma.edu.ai_application.exception;

public class AiGenerationException extends AppException {
    public AiGenerationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
