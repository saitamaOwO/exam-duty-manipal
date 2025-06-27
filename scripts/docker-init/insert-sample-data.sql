-- Insert sample data (matches your existing scripts exactly)

-- Insert schools
INSERT INTO SCHOOL (school_name) VALUES 
('School of Computer Engineering'),
('School of Mathematics'),
('School of Humanities')
ON CONFLICT DO NOTHING;

-- Insert departments
INSERT INTO DEPARTMENT (department_name, school_code) VALUES 
('Computer Science', 1),
('Information Technology', 1),
('Mathematics', 2),
('English', 3)
ON CONFLICT DO NOTHING;

-- Insert specializations
INSERT INTO SPECIALIZATION (specialization_name, school_code) VALUES 
('AIML', 1),
('CSE', 1),
('ICT', 1),
('DSE', 1)
ON CONFLICT DO NOTHING;

-- Insert roles
INSERT INTO ROLE (role_name) VALUES 
('HOD'),
('FACULTY'),
('ADMIN'),
('CLASS_COMMITTEE_CHAIRPERSON')
ON CONFLICT (role_name) DO NOTHING;

-- Insert sample staff (exactly as in your existing scripts)
INSERT INTO STAFF (staff_id, staff_type, academic_rank, first_name, last_name, email, mobile, school_id, department_id, password) VALUES 
('MAHE001', 'TEACHING', 'Professor', 'John', 'Doe', 'john.doe@manipal.edu', 9876543210, 1, 1, 'password123'),
('MAHE002', 'TEACHING', 'Associate Professor', 'Jane', 'Smith', 'jane.smith@manipal.edu', 9876543211, 1, 1, 'password123'),
('MAHE003', 'TEACHING', 'Assistant Professor', 'Bob', 'Johnson', 'bob.johnson@manipal.edu', 9876543212, 1, 1, 'password123'),
('MAHE004', 'TEACHING', 'Assistant Professor', 'Alice', 'Brown', 'alice.brown@manipal.edu', 9876543213, 1, 1, 'password123'),
('MAHE005', 'NON-TEACHING', 'Admin', 'Admin', 'User', 'admin@manipal.edu', 9876543214, 1, 1, 'admin123'),
('MAHE006', 'TEACHING', 'Assistant Professor', 'David', 'Wilson', 'david.wilson@manipal.edu', 9876543215, 1, 1, 'password123'),
('MAHE007', 'TEACHING', 'Assistant Professor', 'Sarah', 'Davis', 'sarah.davis@manipal.edu', 9876543216, 1, 1, 'password123'),
('MAHE008', 'TEACHING', 'Associate Professor', 'Michael', 'Brown', 'michael.brown@manipal.edu', 9876543217, 1, 1, 'password123')
ON CONFLICT (staff_id) DO NOTHING;

-- Insert staff roles (exactly as in your existing scripts)
INSERT INTO STAFF_ROLE (staff_id, role_id) VALUES 
('MAHE001', 1), -- HOD
('MAHE001', 2), -- Also Faculty
('MAHE002', 4), -- CCC
('MAHE002', 2), -- Also Faculty
('MAHE003', 2), -- Faculty
('MAHE004', 2), -- Faculty
('MAHE005', 3), -- Admin
('MAHE006', 2), -- Faculty
('MAHE007', 2), -- Faculty
('MAHE008', 2)  -- Faculty
ON CONFLICT DO NOTHING;

-- Insert sample courses
INSERT INTO COURSE (course_code, course_type, course_name, description, credit, semester, school_code, specialization_code) VALUES 
('CS101', 'theory', 'Data Structures', 'Introduction to Data Structures', 4, '3-UG', 1, 2),
('CS102', 'theory', 'Algorithms', 'Algorithm Design and Analysis', 4, '4-UG', 1, 2),
('CS103', 'theory', 'Database Systems', 'Database Management Systems', 3, '5-UG', 1, 2),
('CS104', 'theory', 'Machine Learning', 'Introduction to ML', 4, '6-UG', 1, 1),
('CS105', 'theory', 'Software Engineering', 'Software Development Lifecycle', 4, '5-UG', 1, 2),
('CS106', 'theory', 'Computer Networks', 'Network Protocols and Architecture', 3, '6-UG', 1, 2)
ON CONFLICT (course_code) DO NOTHING;

-- Insert staff-course mapping
INSERT INTO STAFF_COURSE (staff_id, course_id) VALUES 
('MAHE001', 1),
('MAHE002', 2),
('MAHE003', 3),
('MAHE004', 4),
('MAHE006', 5),
('MAHE007', 6)
ON CONFLICT DO NOTHING;

-- Insert sample exams
INSERT INTO EXAM (academic_year, term, semester, course_code, exam_date, time, no_of_invigilators, no_of_relievers, no_of_squads, created_by) VALUES 
(2025, 'mid-term', '3-UG', 'CS101', '2025-03-15', '8-10AM', 2, 1, 1, 'MAHE005'),
(2025, 'mid-term', '4-UG', 'CS102', '2025-03-16', '10-12NOON', 3, 2, 1, 'MAHE005'),
(2025, 'mid-term', '5-UG', 'CS103', '2025-03-17', '2-4PM', 2, 1, 1, 'MAHE005'),
(2025, 'mid-term', '6-UG', 'CS104', '2025-03-18', '4-6PM', 2, 1, 1, 'MAHE005'),
(2025, 'mid-term', '5-UG', 'CS105', '2025-03-19', '8-10AM', 2, 1, 1, 'MAHE005'),
(2025, 'mid-term', '6-UG', 'CS106', '2025-03-20', '10-12NOON', 3, 2, 1, 'MAHE005')
ON CONFLICT DO NOTHING;

-- Insert staff duty counts
INSERT INTO STAFF_DUTY (staff_id, academic_year, term, duty_count) VALUES 
('MAHE001', 2025, 'mid-term', 0), -- HOD - 0 duties
('MAHE002', 2025, 'mid-term', 0), -- CCC - 0 duties
('MAHE003', 2025, 'mid-term', 4), -- Faculty - 4 duties
('MAHE004', 2025, 'mid-term', 4), -- Faculty - 4 duties
('MAHE006', 2025, 'mid-term', 4), -- Faculty - 4 duties
('MAHE007', 2025, 'mid-term', 4), -- Faculty - 4 duties
('MAHE008', 2025, 'mid-term', 2)  -- Faculty - 2 duties
ON CONFLICT DO NOTHING;