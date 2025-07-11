-- SEQUENCES
CREATE SEQUENCE IF NOT EXISTS common.school_seq START 1;
CREATE SEQUENCE IF NOT EXISTS common.specialization_seq START 1;
CREATE SEQUENCE IF NOT EXISTS common.department_seq START 1;
CREATE SEQUENCE IF NOT EXISTS common.course_seq START 1;
CREATE SEQUENCE IF NOT EXISTS common.role_seq START 1;
CREATE SEQUENCE IF NOT EXISTS common.academic_rank_seq START 1;
CREATE SEQUENCE IF NOT EXISTS examduty.exam_seq START 1;
CREATE SEQUENCE IF NOT EXISTS examduty.exam_slot_seq START 1;
CREATE SEQUENCE IF NOT EXISTS examduty.staff_duty_seq START 1;

-- COMMON SCHEMA TABLES
CREATE TABLE IF NOT EXISTS common.school (
    school_code INTEGER PRIMARY KEY DEFAULT nextval('common.school_seq'),
    school_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS common.specialization (
    specialization_code INTEGER PRIMARY KEY DEFAULT nextval('common.specialization_seq'),
    specialization_name VARCHAR(255) NOT NULL,
    school_code INTEGER REFERENCES common.school(school_code)
);

CREATE TABLE IF NOT EXISTS common.department (
    department_id INTEGER PRIMARY KEY DEFAULT nextval('common.department_seq'),
    department_name VARCHAR(255) NOT NULL,
    school_code INTEGER REFERENCES common.school(school_code)
);

CREATE TABLE IF NOT EXISTS common.academic_rank (
    academic_rank_id INTEGER PRIMARY KEY DEFAULT nextval('common.academic_rank_seq'),
    name VARCHAR(255) NOT NULL,
    cadre_id INTEGER CHECK (cadre_id IN (1, 2, 3, 4, 5)),
    examduty_base_workload INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS common.staff (
    staff_id VARCHAR(50) PRIMARY KEY,
    staff_type VARCHAR(20) CHECK (staff_type IN ('TEACHING', 'NON-TEACHING')),
    academic_rank_id INTEGER REFERENCES common.academic_rank(academic_rank_id),
    academic_rank VARCHAR(100),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mobile BIGINT,
    cabin_location VARCHAR(255),
    examduty_workload INTEGER DEFAULT 0,
    school_id INTEGER REFERENCES common.school(school_code),
    department_id INTEGER REFERENCES common.department(department_id),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS common.role (
    role_id INTEGER PRIMARY KEY DEFAULT nextval('common.role_seq'),
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS common.staff_role (
    staff_id VARCHAR(50) REFERENCES common.staff(staff_id),
    role_id INTEGER REFERENCES common.role(role_id),
    PRIMARY KEY (staff_id, role_id)
);

CREATE TABLE IF NOT EXISTS common.course (
    course_id INTEGER PRIMARY KEY DEFAULT nextval('common.course_seq'),
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_type VARCHAR(20) CHECK (course_type IN ('theory', 'lab')),
    course_name VARCHAR(255) NOT NULL,
    description TEXT,
    credit INTEGER,
    program VARCHAR(10) CHECK (program IN ('UG', 'PG')),
    semester VARCHAR(10),
    school_code INTEGER REFERENCES common.school(school_code),
    specialization_code INTEGER REFERENCES common.specialization(specialization_code),
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'inactive'))
);

CREATE TABLE IF NOT EXISTS common.staff_course (
    staff_id VARCHAR(50) REFERENCES common.staff(staff_id),
    course_id INTEGER REFERENCES common.course(course_id),
    PRIMARY KEY (staff_id, course_id)
);

CREATE TABLE IF NOT EXISTS common.staff_course_group (
    staff_id VARCHAR(50) REFERENCES common.staff(staff_id),
    course_id INTEGER REFERENCES common.course(course_id),
    group_type VARCHAR(50) DEFAULT 'COURSE_COORDINATOR',
    PRIMARY KEY (staff_id, course_id)
);

CREATE TABLE IF NOT EXISTS common.student (
    registration_id INTEGER PRIMARY KEY,
    student_type VARCHAR(20) CHECK (student_type IN ('STUDENT', 'ALUMNI')),
    registration_date DATE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    learner_email VARCHAR(255),
    personal_email VARCHAR(255),
    mobile BIGINT,
    specialization_code INTEGER REFERENCES common.specialization(specialization_code),
    status VARCHAR(20) CHECK (status IN ('ACTIVE', 'INACTIVE')),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS common.student_course (
    registration_id INTEGER REFERENCES common.student(registration_id),
    course_id INTEGER REFERENCES common.course(course_id),
    PRIMARY KEY (registration_id, course_id)
);

-- EXAM DUTY SCHEMA TABLES
CREATE TABLE IF NOT EXISTS examduty.exam (
    exam_id INTEGER PRIMARY KEY DEFAULT nextval('examduty.exam_seq'),
    academic_year INTEGER NOT NULL,
    program_name VARCHAR(10) CHECK (program_name IN ('UG', 'PG')),
    term VARCHAR(20) CHECK (term IN ('mid', 'final')),
    semester VARCHAR(10),
    course_code VARCHAR(20) REFERENCES common.course(course_code),
    exam_date DATE NOT NULL,
    exam_time VARCHAR(50),
    no_of_rooms INTEGER DEFAULT 0,
    no_of_students INTEGER DEFAULT 0,
    variation INTEGER DEFAULT 0,
    no_of_invigilators INTEGER DEFAULT 0,
    no_of_relievers INTEGER DEFAULT 0,
    no_of_squads INTEGER DEFAULT 0,
    other_department_id INTEGER REFERENCES common.department(department_id),
    no_of_required_resources INTEGER DEFAULT 0,
    created_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS examduty.exam_slot (
    exam_duty_id INTEGER PRIMARY KEY DEFAULT nextval('examduty.exam_slot_seq'),
    exam_id INTEGER REFERENCES examduty.exam(exam_id),
    slot_id INTEGER,
    reserved_by VARCHAR(50),
    reserved_date TIMESTAMP,
    alloted_by VARCHAR(20) CHECK (alloted_by IN ('SYSTEM', 'ADMIN')),
    alloted_date TIMESTAMP,
    alloted_to VARCHAR(50),
    duty_assigned VARCHAR(20) CHECK (duty_assigned IN ('INVIGILATOR', 'RELIEVER', 'SQUAD')),
    preferred_by VARCHAR(50),
    preferred_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS examduty.staff_duty (
    staff_duty_id INTEGER PRIMARY KEY DEFAULT nextval('examduty.staff_duty_seq'),
    staff_id VARCHAR(50) REFERENCES common.staff(staff_id),
    academic_year INTEGER NOT NULL,
    term VARCHAR(20) CHECK (term IN ('mid-term', 'final-term')),
    duty_count INTEGER DEFAULT 0,
    UNIQUE(staff_id, academic_year, term)
);