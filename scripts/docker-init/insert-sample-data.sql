-- SAMPLE DATA
INSERT INTO common.academic_rank (name, cadre_id, examduty_base_workload) VALUES 
('Professor', 1, 2), ('Additional Professor', 1, 2), ('Professor of Practice', 1, 2),
('Associate Professor', 2, 2), ('Assistant Professor (Selection Grade)', 3, 4),
('Assistant Professor (Senior Scale)', 3, 4), ('Assistant Professor (Regular)', 4, 4),
('Assistant Professor (Contract)', 4, 4), ('Admin', 5, 0)
ON CONFLICT DO NOTHING;

INSERT INTO common.school (school_name) VALUES 
('School of Computer Engineering'), ('School of Mathematics'), ('School of Humanities')
ON CONFLICT DO NOTHING;

INSERT INTO common.department (department_name, school_code) VALUES 
('Computer Science', 1), ('Information Technology', 1), ('Mathematics', 2), ('English', 3)
ON CONFLICT DO NOTHING;

INSERT INTO common.specialization (specialization_name, school_code) VALUES 
('AIML', 1), ('CSE', 1), ('ICT', 1), ('DSE', 1)
ON CONFLICT DO NOTHING;

INSERT INTO common.role (role_name) VALUES 
('HOD'), ('FACULTY'), ('ADMIN'), ('CLASS_COMMITTEE_CHAIRPERSON')
ON CONFLICT DO NOTHING;

INSERT INTO common.staff (staff_id, staff_type, academic_rank_id, academic_rank, first_name, last_name, email, mobile, school_id, department_id, password, examduty_workload) VALUES 
('MAHE001', 'TEACHING', 1, 'Professor', 'John', 'Doe', 'john.doe@manipal.edu', 9876543210, 1, 1, 'password123', 0),
('MAHE002', 'TEACHING', 4, 'Associate Professor', 'Jane', 'Smith', 'jane.smith@manipal.edu', 9876543211, 1, 1, 'password123', 0),
('MAHE003', 'TEACHING', 7, 'Assistant Professor', 'Bob', 'Johnson', 'bob.johnson@manipal.edu', 9876543212, 1, 1, 'password123', 0),
('MAHE004', 'TEACHING', 7, 'Assistant Professor', 'Alice', 'Brown', 'alice.brown@manipal.edu', 9876543213, 1, 1, 'password123', 0),
('MAHE005', 'NON-TEACHING', 9, 'Admin', 'Admin', 'User', 'admin@manipal.edu', 9876543214, 1, 1, 'admin123', 0),
('MAHE006', 'TEACHING', 7, 'Assistant Professor', 'David', 'Wilson', 'david.wilson@manipal.edu', 9876543215, 1, 1, 'password123', 0),
('MAHE007', 'TEACHING', 8, 'Assistant Professor', 'Sarah', 'Davis', 'sarah.davis@manipal.edu', 9876543216, 1, 1, 'password123', 0),
('MAHE008', 'TEACHING', 4, 'Associate Professor', 'Michael', 'Brown', 'michael.brown@manipal.edu', 9876543217, 1, 1, 'password123', 0)
ON CONFLICT (staff_id) DO NOTHING;

INSERT INTO common.staff_role (staff_id, role_id) VALUES 
('MAHE001', 1), ('MAHE001', 2), ('MAHE002', 4), ('MAHE002', 2), ('MAHE003', 2),
('MAHE004', 2), ('MAHE005', 3), ('MAHE006', 2), ('MAHE007', 2), ('MAHE008', 2)
ON CONFLICT DO NOTHING;

INSERT INTO common.course (course_code, course_type, course_name, description, credit, program, semester, school_code, specialization_code) VALUES 
('CS101', 'theory', 'Data Structures', 'Introduction to Data Structures', 4, 'UG', '3-UG', 1, 2),
('CS102', 'theory', 'Algorithms', 'Algorithm Design and Analysis', 4, 'UG', '4-UG', 1, 2),
('CS103', 'theory', 'Database Systems', 'Database Management Systems', 3, 'UG', '5-UG', 1, 2),
('CS104', 'theory', 'Machine Learning', 'Introduction to ML', 4, 'UG', '6-UG', 1, 1),
('CS105', 'theory', 'Software Engineering', 'Software Development Lifecycle', 4, 'UG', '5-UG', 1, 2),
('CS106', 'theory', 'Computer Networks', 'Network Protocols and Architecture', 3, 'UG', '6-UG', 1, 2)
ON CONFLICT (course_code) DO NOTHING;

INSERT INTO common.staff_course (staff_id, course_id) VALUES 
('MAHE001', 1), ('MAHE002', 2), ('MAHE003', 3), ('MAHE004', 4), ('MAHE006', 5), ('MAHE007', 6)
ON CONFLICT DO NOTHING;

INSERT INTO common.staff_course_group (staff_id, course_id, group_type) VALUES 
('MAHE001', 1, 'COURSE_COORDINATOR'), ('MAHE002', 2, 'COURSE_COORDINATOR'), ('MAHE003', 3, 'COURSE_COORDINATOR'),
('MAHE004', 4, 'COURSE_COORDINATOR'), ('MAHE006', 5, 'COURSE_COORDINATOR'), ('MAHE007', 6, 'COURSE_COORDINATOR')
ON CONFLICT DO NOTHING;

INSERT INTO examduty.exam (academic_year, program_name, term, semester, course_code, exam_date, exam_time, no_of_rooms, no_of_students, variation, no_of_invigilators, no_of_relievers, no_of_squads, created_by) VALUES 
(2025, 'UG', 'mid', '3-UG', 'CS101', '2025-03-15', '9-12.30NOON', 5, 120, 2, 2, 1, 1, 'MAHE005'),
(2025, 'UG', 'mid', '4-UG', 'CS102', '2025-03-16', '2-4.30PM', 8, 180, 3, 3, 2, 1, 'MAHE005'),
(2025, 'UG', 'mid', '5-UG', 'CS103', '2025-03-17', '9-12.30NOON', 6, 150, 2, 2, 1, 1, 'MAHE005'),
(2025, 'UG', 'mid', '6-UG', 'CS104', '2025-03-18', '2-4.30PM', 4, 100, 2, 2, 1, 1, 'MAHE005'),
(2025, 'UG', 'mid', '5-UG', 'CS105', '2025-03-19', '9-12.30NOON', 6, 140, 2, 2, 1, 1, 'MAHE005'),
(2025, 'UG', 'mid', '6-UG', 'CS106', '2025-03-20', '2-4.30PM', 7, 160, 3, 3, 2, 1, 'MAHE005')
ON CONFLICT DO NOTHING;

INSERT INTO examduty.staff_duty (staff_id, academic_year, term, duty_count) VALUES 
('MAHE001', 2025, 'mid-term', 0), -- HOD excluded
('MAHE002', 2025, 'mid-term', 0), -- CC excluded
('MAHE003', 2025, 'mid-term', 4), -- Cadre 4
('MAHE004', 2025, 'mid-term', 4), -- Cadre 4
('MAHE006', 2025, 'mid-term', 4), -- Cadre 4
('MAHE007', 2025, 'mid-term', 4), -- Cadre 4
('MAHE008', 2025, 'mid-term', 2)  -- Cadre 2
ON CONFLICT DO NOTHING;