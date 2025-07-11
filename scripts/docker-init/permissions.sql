-- PERMISSIONS
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA common TO common_user;
GRANT SELECT ON ALL TABLES IN SCHEMA common TO examduty_user;
GRANT UPDATE (examduty_workload) ON common.staff TO examduty_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA examduty TO examduty_user, examduty_admin;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA common TO common_user, examduty_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA examduty TO examduty_user, examduty_admin;
REVOKE ALL ON SCHEMA examduty FROM common_user;
REVOKE ALL ON ALL TABLES IN SCHEMA examduty FROM common_user;