SELECT so.*, ro.r_office_area_id, ro.r_office_name, po_h.p_office_area_id, po_h.p_office_name, po_w.p_office_area_id, po_w.p_office_name
FROM jc_student_order so
INNER JOIN jc_register_office ro ON so.register_office_id = ro.r_office_id
INNER JOIN jc_passport_office po_h ON so.h_passport_office_id = po_h.p_office_id
INNER JOIN jc_passport_office po_w ON so.w_passport_office_id = po_w.p_office_id
WHERE student_order_status = 0 ORDER BY student_order_date;


SELECT so.*, ro.r_office_area_id, ro.r_office_name,
            po_h.p_office_area_id as h_p_office_area_id,
            po_h.p_office_name as h_p_office_name,
            po_w.p_office_area_id as w_p_office_area_id,
            po_w.p_office_name as w_p_office_name,
            soc.*, ro_c.r_office_area_id, ro_c.r_office_name
            FROM jc_student_order so
            INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id
            INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id
            INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id
            INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id
            INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id
            WHERE student_order_status = 0 ORDER BY student_order_date;

            SELECT so.*, ro.r_office_area_id, ro.r_office_name,
            po_h.p_office_area_id as h_p_office_area_id,
            po_h.p_office_name as h_p_office_name,
            po_w.p_office_area_id as w_p_office_area_id,
            po_w.p_office_name as w_p_office_name
            FROM jc_student_order so
            INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id
            INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id
            INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id
            WHERE student_order_status = 0 ORDER BY student_order_date;