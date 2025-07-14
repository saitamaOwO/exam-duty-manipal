-- WORKLOAD UPDATE TRIGGER
CREATE OR REPLACE FUNCTION examduty.update_staff_workload()
RETURNS TRIGGER AS $$
DECLARE
    affected_staff_ids TEXT[] := ARRAY[]::TEXT[];
BEGIN
    IF TG_OP IN ('INSERT', 'UPDATE') THEN
        IF NEW.preferred_by IS NOT NULL THEN
            affected_staff_ids := array_append(affected_staff_ids, NEW.preferred_by);
        END IF;
        IF NEW.reserved_by IS NOT NULL AND NEW.reserved_by != COALESCE(NEW.preferred_by, '') THEN
            affected_staff_ids := array_append(affected_staff_ids, NEW.reserved_by);
        END IF;
    END IF;

    IF TG_OP IN ('UPDATE', 'DELETE') THEN
        IF OLD.preferred_by IS NOT NULL THEN
            affected_staff_ids := array_append(affected_staff_ids, OLD.preferred_by);
        END IF;
        IF OLD.reserved_by IS NOT NULL AND OLD.reserved_by != COALESCE(OLD.preferred_by, '') THEN
            affected_staff_ids := array_append(affected_staff_ids, OLD.reserved_by);
        END IF;
    END IF;

    IF array_length(affected_staff_ids, 1) > 0 THEN
        UPDATE common.staff
        SET examduty_workload = (
            SELECT COUNT(*) FROM examduty.exam_slot es
            WHERE es.preferred_by = staff.staff_id OR es.reserved_by = staff.staff_id
        )
        WHERE staff_id = ANY(SELECT DISTINCT unnest(affected_staff_ids));
    END IF;

    RETURN COALESCE(NEW, OLD);
END;
$$ LANGUAGE plpgsql;