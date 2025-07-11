-- Create schemas
CREATE SCHEMA IF NOT EXISTS common;
CREATE SCHEMA IF NOT EXISTS examduty;

-- Create users for different access levels
CREATE USER common_user WITH PASSWORD 'common123';
CREATE USER examduty_user WITH PASSWORD 'examduty123';
CREATE USER examduty_admin WITH PASSWORD 'examdutyadmin123';

-- Grant schema usage permissions
GRANT USAGE ON SCHEMA common TO common_user, examduty_user, examduty_admin;
GRANT USAGE ON SCHEMA examduty TO examduty_user, examduty_admin;
GRANT CREATE ON SCHEMA common TO examduty_admin;
GRANT CREATE ON SCHEMA examduty TO examduty_admin;

-- Set default privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA common GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO common_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA common GRANT SELECT ON TABLES TO examduty_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA examduty GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO examduty_user, examduty_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA common GRANT USAGE, SELECT ON SEQUENCES TO common_user, examduty_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA examduty GRANT USAGE, SELECT ON SEQUENCES TO examduty_user, examduty_admin;