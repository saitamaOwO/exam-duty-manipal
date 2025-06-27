-- COMMON Tables
CREATE TABLE IF NOT EXISTS SCHOOL (
    school_code INTEGER PRIMARY KEY DEFAULT nextval('school_seq'),
    school_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS SPECIALIZATION (
    specialization_code INTEGER PRIMARY KEY DEFAULT nextval('specialization_seq'),
    specialization_name VARCHAR(255) NOT NULL,
    school_code INTEGER REFERENCES SCHOOL(school_code)
);

CREATE TABLE IF NOT EXISTS DEPARTMENT (
    department_id INTEGER PRIMARY KEY DEFAULT nextval('department_seq'),
    department_name VARCHAR(255) NOT NULL,
    school_code INTEGER REFERENCES SCHOOL(school_code)
);

CREATE TABLE IF NOT EXISTS STAFF (
    staff_id VARCHAR(50) PRIMARY KEY,
    staff_type VARCHAR(20) CHECK (staff_type IN ('TEACHING', 'NON-TEACHING')),
    academic_rank VARCHAR(100),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mobile BIGINT,
    cabin_location VARCHAR(255),
    school_id INTEGER REFERENCES SCHOOL(school_code),
    department_id INTEGER REFERENCES DEPARTMENT(department_id),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ROLE (
    role_id INTEGER PRIMARY KEY DEFAULT nextval('role_seq'),
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS STAFF_ROLE (
    staff_id VARCHAR(50) REFERENCES STAFF(staff_id),
    role_id INTEGER REFERENCES ROLE(role_id),
    PRIMARY KEY (staff_id, role_id)
);

CREATE TABLE IF NOT EXISTS COURSE (
    course_id INTEGER PRIMARY KEY DEFAULT nextval('course_seq'),
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_type VARCHAR(20) CHECK (course_type IN ('theory', 'lab')),
    course_name VARCHAR(255) NOT NULL,
    description TEXT,
    credit INTEGER,
    semester VARCHAR(10),
    school_code INTEGER REFERENCES SCHOOL(school_code),
    specialization_code INTEGER REFERENCES SPECIALIZATION(specialization_code),
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'inactive'))
);

CREATE TABLE IF NOT EXISTS STAFF_COURSE (
    staff_id VARCHAR(50) REFERENCES STAFF(staff_id),
    course_id INTEGER REFERENCES COURSE(course_id),
    PRIMARY KEY (staff_id, course_id)
);

-- EXAM_DUTY Tables
CREATE TABLE IF NOT EXISTS EXAM (
    exam_id INTEGER PRIMARY KEY DEFAULT nextval('exam_seq'),
    academic_year INTEGER NOT NULL,
    term VARCHAR(20) CHECK (term IN ('mid-term', 'final-exam')),
    semester VARCHAR(10),
    course_code VARCHAR(20) REFERENCES COURSE(course_code),
    exam_date DATE NOT NULL,
    time VARCHAR(20),
    no_of_invigilators INTEGER DEFAULT 0,
    no_of_relievers INTEGER DEFAULT 0,
    no_of_squads INTEGER DEFAULT 0,
    other_department_id INTEGER REFERENCES DEPARTMENT(department_id),
    no_of_required_resources INTEGER DEFAULT 0,
    created_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS EXAM_SLOT (
    exam_duty_id INTEGER PRIMARY KEY DEFAULT nextval('exam_slot_seq'),
    exam_id INTEGER REFERENCES EXAM(exam_id),
    slot_id INTEGER,
    preferred_by VARCHAR(50),
    preferred_date TIMESTAMP,
    alloted_by VARCHAR(50),
    alloted_date TIMESTAMP,
    alloted_to VARCHAR(50),
    duty_assigned VARCHAR(20) CHECK (duty_assigned IN ('INVIGILATOR', 'RELIEVER', 'SQUAD'))
);

CREATE TABLE IF NOT EXISTS STAFF_DUTY (
    staff_duty_id INTEGER PRIMARY KEY DEFAULT nextval('staff_duty_seq'),
    staff_id VARCHAR(50) REFERENCES STAFF(staff_id),
    academic_year INTEGER NOT NULL,
    term VARCHAR(20) CHECK (term IN ('mid-term', 'final-term')),
    duty_count INTEGER DEFAULT 0
);
