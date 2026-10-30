
CREATE TABLE IF NOT EXISTS student_grades (
    id BIGSERIAL PRIMARY KEY,
    student_code VARCHAR(20) NOT NULL,
    course_name VARCHAR(200) NOT NULL,
    semester VARCHAR(20) NOT NULL,
    score NUMERIC(4,2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_student_grades_student ON student_grades (student_code);

-- Test xem sinh viên này có xem được điểm của sinh viên khi hỏi AI không
INSERT INTO student_grades (student_code, course_name, semester, score) VALUES
('SV001', 'Cơ sở dữ liệu', '2025-1', 8.5),
('SV001', 'Mạng máy tính', '2025-1', 7.0),
('SV002', 'Cơ sở dữ liệu', '2025-1', 5.0),
('SV002', 'Mạng máy tính', '2025-1', 9.0);