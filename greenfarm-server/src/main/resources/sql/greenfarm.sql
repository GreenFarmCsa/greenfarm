create table gf_farm(
    farm_id int not null auto_increment primary key,
    farm_name varchar(50) not null,
    introduction varchar(1000),
    username varchar(50),
    total_area int,
    idle_area int,
    location varchar(100),
    latitude_longitude varchar(200),
    icon_url varchar(2000),
    image_url text,
    vr_url text,
    suited_crops varchar(100),
    rent_period varchar(100),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_seed(
    seed_id int not null auto_increment primary key,
    product_id int,
    seed_name varchar(50),
    seed_introduction text,
    production varchar(100),
    lowest_planting varchar(50),
    seed_price decimal(19,4), 
    fertilizer_name varchar(50),
    fertilizer_price decimal(19,4),
    fertilizer_amount varchar(50),
    watering_quantity varchar(50),
    mature_period int,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_land(
    land_id int not null auto_increment primary key,
    farm_id int not null,
    price decimal(19,4),
    is_rent tinyint(1),
    suited_crops varchar(100),
    area int,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_rent(
    rent_id int not null auto_increment primary key,
    land_id int not null,
    farm_id int not null,
    seed_id int not null,
    username varchar(50),
    rent_price decimal(19,4),
    area int,
    rent_start_time date,
    rent_end_time date,
    confirm_crops varchar(50),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_product(
    product_id int not null auto_increment primary key,
    farm_id int not null,
    land_id int not null,
    username varchar(50),
    product_name varchar(50),
    category varchar(50),
    introduction varchar(2000),
    weight varchar(50),
    price decimal(19,4),
    carbon_credit int,
    number int,
    sale_number int,
    like_number int,
    image_url text,
    vedio_url text,
    identifications varchar(100),
    carbon_emission decimal(19,4),
    donate_amount decimal(19,4),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_plant_task(
    task_id int not null auto_increment primary key,
    username varchar(50),
    land_id int not null,
    seed_id int not null,
    product_id int,
    status varchar(50),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_task_step(
    step_id int not null auto_increment primary key,
    task_id int,
    carbon_credit int,
    plant_no int,
    plant_step varchar(500),
    status varchar(50),
    begin_time date,
    end_time date,
    remark varchar(3000)
);

create table gf_step_template(
    template_id int not null auto_increment primary key,
    seed_id int not null,
    carbon_credit int,
    plant_no int,
    plant_step varchar(500),
    vedio_url text,
    begin_time date,
    end_time date,
    remark varchar(3000)
);

create table gf_task_detail(
    detail_id int not null auto_increment primary key,
    step_id int not null,
    username varchar(50),
    oper_record text,
    location varchar(500),
    status varchar(50),
    create_time datetime,
    modify_time datetime,
    remark varchar(3000)
);



create table gf_order(
    order_id int not null auto_increment primary key,
    username varchar(50),
    address varchar(100),
    money decimal(19,4),
    carbon_credit int,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_order_detail(
    detail_id int not null auto_increment primary key,
    order_id int not null,
    product_id int not null,
    amount int,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_shopping_cart(
    cart_id int not null auto_increment primary key,
    username varchar(50),
    product_id int not null,
    number int,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);


create table gf_carbon_footprint(
    footprint_id int not null auto_increment primary key,
    footprint_name varchar(50),
    footprint_category varchar(50),
    farm_id int not null,
    username varchar(50),
    carbon_reduction decimal(19,4),
    carbon_credit int,
    location varchar(100),
    create_time datetime,
    modify_time datetime,
    remark varchar(3000)
);

create table gf_carbon_credit_daily (
    credit_daily_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    carbon_credit int,
    carbon_date date,
    type char(1)
);

create table gf_community (
    community_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    farm_id int NOT NULL,
    community_name varchar(300),
    introduction varchar(1000),
    community_image_url varchar(2000),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_community_member (
    community_id int NOT NULL,
    username varchar(50),
    create_time datetime,
    modify_time datetime,
    remark varchar(500),
    constraint PK_member primary key(community_id ,username)
);

create table gf_topic (
    topic_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    community_id int NOT NULL,
    username varchar(50),
    topic_name varchar(50) NOT NULL,
    topic_content varchar(3000),
    topic_image_url varchar(2000),
    like_sum int default 0,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_comment (
    comment_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    topic_id int NOT NULL,
    username varchar(50),
    comment_content text,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_product_comment (
    comment_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_id int NOT NULL,
    username varchar(50),
    comment_content text,
    comment_image text,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_user (
    username varchar(50) NOT NULL PRIMARY KEY,
    rolename varchar(50),
    fullname varchar(100),
    password varchar(100),
    phone varchar(50),
    email varchar(50),
    sex varchar(10),
    location varchar(100),
    icon_url varchar(2000),
    description varchar(100),
    carbon_credit int,
    carbon_medals varchar(500),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_finance_product (
    finance_product_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    finance_product_name varchar(50),
    finance_product_no varchar(50),
    finance_product_desc varchar(500),
    org_name varchar(50),
    finance_product_category varchar(50),
    finance_limit varchar(50),
    valid_period varchar(100),
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_user_finance (
	user_finance_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    finance_product_id int NOT NULL,
    username varchar(50),
    finance_limit varchar(50),
    start_date date,
    end_date date,
    create_time datetime,
    modify_time datetime,
    remark varchar(500)
);

create table gf_live (
    live_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    farm_id int NOT NULL,
    step_id int not null,
    username varchar(50),
    status varchar(50),
    create_time datetime,
    modify_time datetime,
    tick_time datetime,
    remark varchar(500)
);
create table gf_like (
    like_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    topic_id int,
    product_id int
);
create table gf_live_video_bat (
    live_id varchar(50),
    modify_time datetime
);

