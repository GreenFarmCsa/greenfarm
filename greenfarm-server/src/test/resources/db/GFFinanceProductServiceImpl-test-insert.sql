INSERT INTO  gf_finance_product (finance_product_id,finance_product_name, finance_product_no,  finance_product_desc, org_name, finance_product_category,    finance_limit, valid_period, create_time,  modify_time, remark)
    VALUES (1111,'pName', 'test',  'test', 'test','test',   'test', 'test',now(),    now(), 'test');
INSERT INTO gf_user_finance (user_finance_id,finance_product_id, username, finance_limit,  start_date, end_date, create_time,  modify_time, remark)
    VALUES (2222,1111, 'MVCTEST', 'test',  now(), now(),now(),  now(), 'test');