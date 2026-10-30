package com.kma.edu.ai_application.tools;

import com.kma.edu.ai_application.dto.GradeResult;
import com.kma.edu.ai_application.exception.ErrorCode;
import com.kma.edu.ai_application.exception.ToolException;
import com.kma.edu.ai_application.repository.GradeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GradeTools {

    public static final String STUDENT_CODE_KEY = "studentCode";

    private final GradeRepository gradeRepository;

    @Tool(
            description = "Tra cứu điểm các học phần của CHÍNH sinh viên đang đặt câu hỏi. "
                    + "Có thể lọc theo tên môn học, bỏ trống để lấy tất cả các môn.")
    public List<GradeResult> getMyGrades(
            @ToolParam(description = "Tên môn học cần tra, ví dụ 'Cơ sở dữ liệu'", required = false) String courseName,
            ToolContext toolContext) {

        // chỉ tra được dữ liệu từ studentCode (trước đó lấy từ JWT và lưu trong toolContext)
        String studentCode = (String) toolContext.getContext().get(STUDENT_CODE_KEY);

        if (studentCode == null) {
            throw new ToolException(ErrorCode.STUDENT_CONTEXT_MISSING);
        }

        log.info("Tool getMyGrades: student={}, course={}", studentCode, courseName);

        // xử lý khi courseName null
        String keyword = courseName == null ? "" : courseName.trim();

        var grades = keyword.isEmpty()
                ? gradeRepository.findByStudentCode(studentCode)
                : gradeRepository.findByStudentCodeAndCourseNameContainingIgnoreCase(studentCode, keyword);

        return grades.stream()
                .map(grade -> new GradeResult(grade.getCourseName(), grade.getSemester(), grade.getScore()))
                .toList();
    }
}
