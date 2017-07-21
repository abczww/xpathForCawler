CREATE DATABASE IF NOT EXISTS bdap default charset utf8 COLLATE utf8_general_ci; 

DROP TABLE IF EXISTS `bdap_article_temp`;
DROP TABLE IF EXISTS `bdap_capture_record`; 

CREATE TABLE `bdap_article_temp` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `kspId` int(4) NOT NULL COMMENT 'which crawler inserted this record',
  `gatherTime` timestamp not NULL default current_timestamp comment  '采集时间',
  `carModel` varchar(32) not NULL COMMENT '车型(问题发现：【BJ212;BJ20;BJ40;BJ80;】、定制改装【陆丰；哈弗；】)',
  `carFirm` varchar(32) not NULL COMMENT '厂商(问题发现)',
  `type` int(8) DEFAULT -1 COMMENT '类型（1:行业动态 ；2:问题发现 ；3:定制改装 ；4:竞品分析 ；）',
  `website` varchar(512) not NULL COMMENT '来源网站',
  `url` varchar(1024) not NULL COMMENT '原始URL',
  `author` varchar(512) not NULL COMMENT '帖子信息-用户',
  `time` varchar(32) not NULL COMMENT '帖子信息-时间',
  `date` varchar(16) not null COMMENT '帖子信息-日期',
  `title` varchar(512) not NULL COMMENT '帖子信息-标题',
  `content` longtext not null COMMENT '帖子信息-全文',
  `content2` longtext not null COMMENT '帖子信息-全文-检索',
  `hits` int(4) COMMENT '点击数',
  `replies` int(4) COMMENT '回复数',
  `createdBy` varchar(32),
  `createdTime` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
  `updatedBy` varchar(32),
  `updatedTime` timestamp default CURRENT_TIMESTAMP,
  `deleted` int(1) default 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

create table `x` (
	`id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `kspId` int(4) NOT NULL COMMENT 'which crawler inserted this record',
    `kspName`varchar(64) not null comment 'the name of the crawler',
    `url` varchar(1024) not null comment 'the ksp captured url',
    `capture_start_time` timestamp not null default current_timestamp comment 'insert time',
    `capture_end_time` timestamp not null default '1970-01-02 00:00:00',
    `capture_account` int(4) default 0,
	`createdBy` varchar(32),
	`createdTime` timestamp not NULL default CURRENT_TIMESTAMP comment '插入时间',
	`updatedBy` varchar(32),
	`updatedTime` timestamp default CURRENT_TIMESTAMP,
    `deleted` int(1) default 0,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;



