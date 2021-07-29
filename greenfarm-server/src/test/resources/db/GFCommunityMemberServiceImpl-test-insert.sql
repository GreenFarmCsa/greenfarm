INSERT INTO gf_community_member (community_id, username, create_time, modify_time, remark) VALUES (1, 'jz', '2021-07-15 10:26:43', '2021-07-15 10:26:43', 'string');
insert into gf_farm (farm_id,farm_name, introduction, username, total_area, idle_area, location, icon_url, rent_period, create_time, modify_time, remark, suited_crops, latitude_longitude, image_url, vr_url) values (101,'test','test','jz',12,12,'test','test','test',now(),now(),'test','test','test','test','test');
insert into gf_community values(1,101,'jzComm',null,null,null,null,null)
