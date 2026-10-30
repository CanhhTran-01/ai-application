package com.kma.edu.ai_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // --- GENERAL ERRORS ---
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_PARAMETER(100000, "Thiếu tham số bắt buộc", HttpStatus.BAD_REQUEST),

    // --- STUDENT ERRORS ---
    STUDENT_CONTEXT_MISSING(7001, "Thiếu thông tin sinh viên trong ngữ cảnh", HttpStatus.INTERNAL_SERVER_ERROR),
    STUDENT_CONTEXT_INVALID(7002, "Thông tin sinh viên trong ngữ cảnh không hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR),
    STUDENT_NOT_FOUND(7003, "Không tìm thấy thông tin sinh viên", HttpStatus.NOT_FOUND),
    STUDENT_ACCESS_DENIED(7004, "Bạn không có quyền truy cập thông tin sinh viên này", HttpStatus.FORBIDDEN),

    QUIZ_SOURCE_NOT_FOUND(8005, "Không tìm thấy nguồn dữ liệu câu hỏi trắc nghiệm", HttpStatus.NOT_FOUND),

    // --- AI ERRORS ---
    AI_SERVICE_UNAVAILABLE(8001, "Dịch vụ AI hiện không phản hồi", HttpStatus.SERVICE_UNAVAILABLE),
    AI_GENERATION_FAILED(8002, "AI không thể tạo ra kết quả phù hợp", HttpStatus.INTERNAL_SERVER_ERROR),
    AI_INVALID_OUTPUT_FORMAT(8003, "AI trả về dữ liệu sai cấu trúc JSON", HttpStatus.BAD_REQUEST),
    AI_BUSINESS_LOGIC_ERROR(8004, "Kết quả từ AI vi phạm logic nghiệp vụ", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
