INSERT INTO gf_topic (topic_id, community_id, username, topic_name, topic_content, like_sum, create_time, modify_time, remark, topic_image_url) VALUES (777, 1, 'jz', 'ttt', 'string', 0, '2021-07-15 07:40:22', '2021-07-15 07:40:22', 'string', 'string');
INSERT INTO gf_community_member (community_id, username, create_time, modify_time, remark) VALUES (1, 'jz', '2021-07-15 10:26:43', '2021-07-15 10:26:43', 'string');
insert into gf_farm (farm_id,farm_name, introduction, username, total_area, idle_area, location, icon_url, rent_period, create_time, modify_time, remark, suited_crops, latitude_longitude, image_url, vr_url) values (101,'test','test','jz',12,12,'test','test','test',now(),now(),'test','test','test','test','test');
insert into gf_community values(1,101,'jzComm',null,null,null,null,null);
INSERT INTO gf_like (username, topic_id, product_id) VALUES ('jz', 777, null);
INSERT INTO gf_comment (comment_id, topic_id, username, comment_content, create_time, modify_time, remark) VALUES (1, 777, 'jz', 'test-content', '2021-07-09 08:58:38', '2021-07-09 08:58:38', 'test-remark');
INSERT INTO gf_user (username, rolename, fullname,location, password,   phone, email, sex,  icon_url, description, carbon_credit,
                     carbon_medals, create_time, modify_time,  remark)
VALUES ('jz', 'c','tset','test', '123456',  '18703694903', '18703694903@163.com', 'man',
        'a.png', 'goodtest', 50, 'test', now(), now(), 'test');