DROP TABLE IF EXISTS `bdap_article_temp`;
DROP TABLE IF EXISTS `bdap_capture_record`; 
DROP TABLE IF EXISTS `bdap_car`;
DROP TABLE IF EXISTS `bdap_car_manufacturer`;
DROP table if exists `bdap_car_sales_volume`;
DROP table if exists `bdap_article_comment_temp`;


CREATE TABLE `bdap_article_temp` (
  `article_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `ksp_id` int(4) NOT NULL COMMENT 'which crawler inserted this record',
  `gather_time` timestamp not NULL default current_timestamp comment  '采集时间',
  `car_model` varchar(32)  COMMENT '车型(问题发现：【BJ212;BJ20;BJ40;BJ80;】、定制改装【陆丰；哈弗；】)',
  `car_firm` varchar(32)  COMMENT '厂商(问题发现)',
  `type` int(8) DEFAULT -1 COMMENT '类型（1:行业动态 ；2:问题发现 ；3:定制改装 ；4:竞品分析 ；）',
  `website` varchar(512) not NULL COMMENT '来源网站',
  `url` varchar(1024) not NULL COMMENT '原始URL',
  `author` varchar(512) not NULL COMMENT '帖子信息-用户',
  `time` varchar(64) not NULL COMMENT '帖子信息-时间',
  `date` varchar(16) not null COMMENT '帖子信息-日期',
  `title` varchar(512) not NULL COMMENT '帖子信息-标题',
  `content` longtext not null COMMENT '帖子信息-全文',
  `content2` longtext not null COMMENT '帖子信息-全文-检索',
  `hits` int(4) COMMENT '点击数',
  `replies` int(4) COMMENT '回复数',
  `created_by` varchar(32),
  `created_time` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
  `updated_by` varchar(32),
  `updated_time` timestamp default CURRENT_TIMESTAMP,
  `deleted` int(1) default 0,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


CREATE TABLE `bdap_article_comment_temp` (
  `comment_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `comments` varchar(2048),
  `comment_time` varchar(64),
  `comment_author` varchar(32),
  `created_by` varchar(32),
  `created_time` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
  `updated_by` varchar(32),
  `updated_time` timestamp default CURRENT_TIMESTAMP,
  `deleted` int(1) default 0,
  PRIMARY KEY (`comment_id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


create table `bdap_capture_record` (
	`cr_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `ksp_id` int(4) NOT NULL COMMENT 'which crawler inserted this record',
    `ksp_name`varchar(64) not null comment 'the name of the crawler',
    `url` varchar(1024) not null comment 'the ksp captured url',
    `is_running` int(1) default 0,
    `capture_start_time` timestamp not null default current_timestamp comment 'insert time',
    `capture_end_time` timestamp not null default '1970-01-02 00:00:00',
    `capture_account` int(4) default 0,
	`created_by` varchar(32),
	`created_time` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
	`updated_by` varchar(32),
	`updated_time` timestamp default CURRENT_TIMESTAMP,
    `deleted` int(1) default 0,
    PRIMARY KEY (`cr_id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


create table `bdap_car_manufacturer`(
	`manu_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `name` varchar(64) not null UNIQUE comment '名称',
    `createdBy` varchar(32),
	`createdTime` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
	`updatedBy` varchar(32),
	`updatedTime` timestamp default CURRENT_TIMESTAMP,
    `deleted` int(1) default 0,
    PRIMARY KEY (`manu_id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


create table `bdap_car`(
	`car_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `car_name` varchar(64) not null UNIQUE comment '名称',
    `manu_id`  int(32),
    `createdBy` varchar(32),
	`createdTime` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
	`updatedBy` varchar(32),
	`updatedTime` timestamp default CURRENT_TIMESTAMP,
    `deleted` int(1) default 0,
    constraint `manu_id_fk` foreign key(`car_id`) references bdap_car_manufacturer(`manu_id`),
    PRIMARY KEY (`car_id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


create table `bdap_car_sales_volume`(
	`sale_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `car_name` varchar(64),
    PRIMARY KEY (`sale_id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;



