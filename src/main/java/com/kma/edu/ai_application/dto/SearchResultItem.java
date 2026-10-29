package com.kma.edu.ai_application.dto;

import java.util.Map;

public record SearchResultItem(String content, Double score, Map<String, Object> metadata) {}
