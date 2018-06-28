/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ti

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-28 15:44:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `union_id` varchar(100) DEFAULT NULL,
  `mobile_number` varchar(20) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '预备扩展的字段，微信注册的话无需密码',
  `level` int(11) DEFAULT '1' COMMENT '用户等级',
  `bonus` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_user
-- ----------------------------

-- ----------------------------
-- Table structure for app_user_bonus
-- ----------------------------
DROP TABLE IF EXISTS `app_user_bonus`;
CREATE TABLE `app_user_bonus` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `event_msg` varchar(255) DEFAULT NULL,
  `bonus` int(11) DEFAULT NULL COMMENT '积分变动，正负表示花费和增加',
  `create` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_user_bonus
-- ----------------------------

-- ----------------------------
-- Table structure for app_user_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `app_user_subscribe`;
CREATE TABLE `app_user_subscribe` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `topic` varchar(50) DEFAULT 'token' COMMENT '订阅钱包wallet或者Token',
  `update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_user_subscribe
-- ----------------------------

-- ----------------------------
-- Table structure for app_wallet_tag
-- ----------------------------
DROP TABLE IF EXISTS `app_wallet_tag`;
CREATE TABLE `app_wallet_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `wallet` varchar(100) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_wallet_tag
-- ----------------------------

-- ----------------------------
-- Table structure for bc_block
-- ----------------------------
DROP TABLE IF EXISTS `bc_block`;
CREATE TABLE `bc_block` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `hash` varchar(100) NOT NULL,
  `number` bigint(20) unsigned NOT NULL,
  `parent_hash` varchar(100) NOT NULL,
  `coinbase` varchar(100) NOT NULL,
  `difficulty` bigint(20) unsigned NOT NULL,
  `gas_limit` decimal(50,0) NOT NULL,
  `gas_used` decimal(50,0) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `number` (`number`),
  KEY `coinbase` (`coinbase`),
  KEY `hash` (`hash`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (2000000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (4000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (6000000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (8000000) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (10000000) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (12000000) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (14000000) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (16000000) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (18000000) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Records of bc_block
-- ----------------------------

-- ----------------------------
-- Table structure for bc_contract
-- ----------------------------
DROP TABLE IF EXISTS `bc_contract`;
CREATE TABLE `bc_contract` (
  `contract_address` varchar(100) NOT NULL,
  `block_hash` varchar(100) NOT NULL,
  `block_number` bigint(20) unsigned NOT NULL,
  `tx_hash` varchar(100) NOT NULL,
  PRIMARY KEY (`contract_address`),
  KEY `token_address` (`contract_address`),
  KEY `tx_hash` (`tx_hash`),
  KEY `block_hash` (`block_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_contract
-- ----------------------------

-- ----------------------------
-- Table structure for bc_current_block
-- ----------------------------
DROP TABLE IF EXISTS `bc_current_block`;
CREATE TABLE `bc_current_block` (
  `bc_type` varchar(20) NOT NULL DEFAULT 'ETH',
  `block_number` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录当前已处理完成的block，仅仅更新，仅一条记录',
  PRIMARY KEY (`bc_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_current_block
-- ----------------------------
INSERT INTO `bc_current_block` VALUES ('ETH', '0');

-- ----------------------------
-- Table structure for bc_erc20_token
-- ----------------------------
DROP TABLE IF EXISTS `bc_erc20_token`;
CREATE TABLE `bc_erc20_token` (
  `token_address` varchar(42) NOT NULL,
  `token_name` mediumtext CHARACTER SET utf8mb4 NOT NULL,
  `symbol` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `total_supply` varchar(100) NOT NULL,
  `decimals` bigint(20) NOT NULL DEFAULT '0',
  `holders` bigint(10) NOT NULL DEFAULT '0' COMMENT '账户数量',
  `transfers` bigint(15) NOT NULL DEFAULT '0' COMMENT '交易次数',
  `tx_hash` varchar(66) NOT NULL,
  `block_hash` varchar(66) NOT NULL,
  `block_number` bigint(10) unsigned NOT NULL,
  PRIMARY KEY (`token_address`),
  KEY `token_address` (`token_address`),
  KEY `tx_hash` (`tx_hash`),
  KEY `block_hash` (`block_hash`),
  KEY `symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_erc20_token
-- ----------------------------

-- ----------------------------
-- Table structure for bc_erc20_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_erc20_transaction`;
CREATE TABLE `bc_erc20_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_hash` varchar(100) NOT NULL,
  `token_address` varchar(100) NOT NULL,
  `block_hash` varchar(100) NOT NULL,
  `block_number` bigint(20) unsigned NOT NULL,
  `gas` decimal(50,0) NOT NULL,
  `gas_price` decimal(50,0) NOT NULL,
  `send_address` varchar(100) NOT NULL,
  `receive_address` varchar(100) NOT NULL DEFAULT '',
  `value` varchar(100) NOT NULL DEFAULT '0',
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tx_hash` (`tx_hash`),
  KEY `token_address` (`token_address`),
  KEY `block_hash` (`block_hash`),
  KEY `block_number` (`block_number`),
  KEY `send_address` (`send_address`),
  KEY `receive_address` (`receive_address`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (2000000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (4000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (6000000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (8000000) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (10000000) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (12000000) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (14000000) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (16000000) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (18000000) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (20000000) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (22000000) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (24000000) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (26000000) ENGINE = InnoDB,
 PARTITION p13 VALUES LESS THAN (28000000) ENGINE = InnoDB,
 PARTITION p14 VALUES LESS THAN (30000000) ENGINE = InnoDB,
 PARTITION p15 VALUES LESS THAN (32000000) ENGINE = InnoDB,
 PARTITION p16 VALUES LESS THAN (34000000) ENGINE = InnoDB,
 PARTITION p17 VALUES LESS THAN (36000000) ENGINE = InnoDB,
 PARTITION p18 VALUES LESS THAN (38000000) ENGINE = InnoDB,
 PARTITION p19 VALUES LESS THAN (40000000) ENGINE = InnoDB,
 PARTITION p20 VALUES LESS THAN (42000000) ENGINE = InnoDB,
 PARTITION p21 VALUES LESS THAN (44000000) ENGINE = InnoDB,
 PARTITION p22 VALUES LESS THAN (46000000) ENGINE = InnoDB,
 PARTITION p23 VALUES LESS THAN (48000000) ENGINE = InnoDB,
 PARTITION p24 VALUES LESS THAN (50000000) ENGINE = InnoDB,
 PARTITION p25 VALUES LESS THAN (52000000) ENGINE = InnoDB,
 PARTITION p26 VALUES LESS THAN (54000000) ENGINE = InnoDB,
 PARTITION p27 VALUES LESS THAN (56000000) ENGINE = InnoDB,
 PARTITION p28 VALUES LESS THAN (58000000) ENGINE = InnoDB,
 PARTITION p29 VALUES LESS THAN (60000000) ENGINE = InnoDB,
 PARTITION p30 VALUES LESS THAN (62000000) ENGINE = InnoDB,
 PARTITION p31 VALUES LESS THAN (64000000) ENGINE = InnoDB,
 PARTITION p32 VALUES LESS THAN (66000000) ENGINE = InnoDB,
 PARTITION p33 VALUES LESS THAN (68000000) ENGINE = InnoDB,
 PARTITION p34 VALUES LESS THAN (70000000) ENGINE = InnoDB,
 PARTITION p35 VALUES LESS THAN (72000000) ENGINE = InnoDB,
 PARTITION p36 VALUES LESS THAN (74000000) ENGINE = InnoDB,
 PARTITION p37 VALUES LESS THAN (76000000) ENGINE = InnoDB,
 PARTITION p38 VALUES LESS THAN (78000000) ENGINE = InnoDB,
 PARTITION p39 VALUES LESS THAN (80000000) ENGINE = InnoDB,
 PARTITION p40 VALUES LESS THAN (82000000) ENGINE = InnoDB,
 PARTITION p41 VALUES LESS THAN (84000000) ENGINE = InnoDB,
 PARTITION p42 VALUES LESS THAN (86000000) ENGINE = InnoDB,
 PARTITION p43 VALUES LESS THAN (88000000) ENGINE = InnoDB,
 PARTITION p44 VALUES LESS THAN (90000000) ENGINE = InnoDB,
 PARTITION p45 VALUES LESS THAN (92000000) ENGINE = InnoDB,
 PARTITION p46 VALUES LESS THAN (94000000) ENGINE = InnoDB,
 PARTITION p47 VALUES LESS THAN (96000000) ENGINE = InnoDB,
 PARTITION p48 VALUES LESS THAN (98000000) ENGINE = InnoDB,
 PARTITION p49 VALUES LESS THAN (100000000) ENGINE = InnoDB,
 PARTITION p50 VALUES LESS THAN (102000000) ENGINE = InnoDB,
 PARTITION p51 VALUES LESS THAN (104000000) ENGINE = InnoDB,
 PARTITION p52 VALUES LESS THAN (106000000) ENGINE = InnoDB,
 PARTITION p53 VALUES LESS THAN (108000000) ENGINE = InnoDB,
 PARTITION p54 VALUES LESS THAN (110000000) ENGINE = InnoDB,
 PARTITION p55 VALUES LESS THAN (112000000) ENGINE = InnoDB,
 PARTITION p56 VALUES LESS THAN (114000000) ENGINE = InnoDB,
 PARTITION p57 VALUES LESS THAN (116000000) ENGINE = InnoDB,
 PARTITION p58 VALUES LESS THAN (118000000) ENGINE = InnoDB,
 PARTITION p59 VALUES LESS THAN (120000000) ENGINE = InnoDB,
 PARTITION p60 VALUES LESS THAN (122000000) ENGINE = InnoDB,
 PARTITION p61 VALUES LESS THAN (124000000) ENGINE = InnoDB,
 PARTITION p62 VALUES LESS THAN (126000000) ENGINE = InnoDB,
 PARTITION p63 VALUES LESS THAN (128000000) ENGINE = InnoDB,
 PARTITION p64 VALUES LESS THAN (130000000) ENGINE = InnoDB,
 PARTITION p65 VALUES LESS THAN (132000000) ENGINE = InnoDB,
 PARTITION p66 VALUES LESS THAN (134000000) ENGINE = InnoDB,
 PARTITION p67 VALUES LESS THAN (136000000) ENGINE = InnoDB,
 PARTITION p68 VALUES LESS THAN (138000000) ENGINE = InnoDB,
 PARTITION p69 VALUES LESS THAN (140000000) ENGINE = InnoDB,
 PARTITION p70 VALUES LESS THAN (142000000) ENGINE = InnoDB,
 PARTITION p71 VALUES LESS THAN (144000000) ENGINE = InnoDB,
 PARTITION p72 VALUES LESS THAN (146000000) ENGINE = InnoDB,
 PARTITION p73 VALUES LESS THAN (148000000) ENGINE = InnoDB,
 PARTITION p74 VALUES LESS THAN (150000000) ENGINE = InnoDB,
 PARTITION p75 VALUES LESS THAN (152000000) ENGINE = InnoDB,
 PARTITION p76 VALUES LESS THAN (154000000) ENGINE = InnoDB,
 PARTITION p77 VALUES LESS THAN (156000000) ENGINE = InnoDB,
 PARTITION p78 VALUES LESS THAN (158000000) ENGINE = InnoDB,
 PARTITION p79 VALUES LESS THAN (160000000) ENGINE = InnoDB,
 PARTITION p80 VALUES LESS THAN (162000000) ENGINE = InnoDB,
 PARTITION p81 VALUES LESS THAN (164000000) ENGINE = InnoDB,
 PARTITION p82 VALUES LESS THAN (166000000) ENGINE = InnoDB,
 PARTITION p83 VALUES LESS THAN (168000000) ENGINE = InnoDB,
 PARTITION p84 VALUES LESS THAN (170000000) ENGINE = InnoDB,
 PARTITION p85 VALUES LESS THAN (172000000) ENGINE = InnoDB,
 PARTITION p86 VALUES LESS THAN (174000000) ENGINE = InnoDB,
 PARTITION p87 VALUES LESS THAN (176000000) ENGINE = InnoDB,
 PARTITION p88 VALUES LESS THAN (178000000) ENGINE = InnoDB,
 PARTITION p89 VALUES LESS THAN (180000000) ENGINE = InnoDB,
 PARTITION p90 VALUES LESS THAN (182000000) ENGINE = InnoDB,
 PARTITION p91 VALUES LESS THAN (184000000) ENGINE = InnoDB,
 PARTITION p92 VALUES LESS THAN (186000000) ENGINE = InnoDB,
 PARTITION p93 VALUES LESS THAN (188000000) ENGINE = InnoDB,
 PARTITION p94 VALUES LESS THAN (190000000) ENGINE = InnoDB,
 PARTITION p95 VALUES LESS THAN (192000000) ENGINE = InnoDB,
 PARTITION p96 VALUES LESS THAN (194000000) ENGINE = InnoDB,
 PARTITION p97 VALUES LESS THAN (196000000) ENGINE = InnoDB,
 PARTITION p98 VALUES LESS THAN (198000000) ENGINE = InnoDB,
 PARTITION p99 VALUES LESS THAN (200000000) ENGINE = InnoDB,
 PARTITION p100 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Records of bc_erc20_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for bc_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_transaction`;
CREATE TABLE `bc_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `hash` varchar(100) NOT NULL,
  `block_hash` varchar(100) NOT NULL,
  `block_number` bigint(20) NOT NULL,
  `gas` decimal(50,0) unsigned NOT NULL,
  `gas_price` decimal(50,0) unsigned NOT NULL,
  `send_address` varchar(100) NOT NULL,
  `receive_address` varchar(100) NOT NULL DEFAULT '',
  `value` decimal(50,0) NOT NULL DEFAULT '0',
  `data` mediumtext NOT NULL,
  `timestamp` datetime NOT NULL,
  `tx_type` varchar(20) NOT NULL DEFAULT '1' COMMENT '交易类型：1、以太币交易；2、合约调用；3、创建合约；',
  PRIMARY KEY (`id`),
  KEY `hash` (`hash`),
  KEY `block_hash` (`block_hash`),
  KEY `block_number` (`block_number`),
  KEY `send_address` (`send_address`),
  KEY `receive_address` (`receive_address`),
  KEY `tx_type` (`tx_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (2000000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (4000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (6000000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (8000000) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (10000000) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (12000000) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (14000000) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (16000000) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (18000000) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (20000000) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (22000000) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (24000000) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (26000000) ENGINE = InnoDB,
 PARTITION p13 VALUES LESS THAN (28000000) ENGINE = InnoDB,
 PARTITION p14 VALUES LESS THAN (30000000) ENGINE = InnoDB,
 PARTITION p15 VALUES LESS THAN (32000000) ENGINE = InnoDB,
 PARTITION p16 VALUES LESS THAN (34000000) ENGINE = InnoDB,
 PARTITION p17 VALUES LESS THAN (36000000) ENGINE = InnoDB,
 PARTITION p18 VALUES LESS THAN (38000000) ENGINE = InnoDB,
 PARTITION p19 VALUES LESS THAN (40000000) ENGINE = InnoDB,
 PARTITION p20 VALUES LESS THAN (42000000) ENGINE = InnoDB,
 PARTITION p21 VALUES LESS THAN (44000000) ENGINE = InnoDB,
 PARTITION p22 VALUES LESS THAN (46000000) ENGINE = InnoDB,
 PARTITION p23 VALUES LESS THAN (48000000) ENGINE = InnoDB,
 PARTITION p24 VALUES LESS THAN (50000000) ENGINE = InnoDB,
 PARTITION p25 VALUES LESS THAN (52000000) ENGINE = InnoDB,
 PARTITION p26 VALUES LESS THAN (54000000) ENGINE = InnoDB,
 PARTITION p27 VALUES LESS THAN (56000000) ENGINE = InnoDB,
 PARTITION p28 VALUES LESS THAN (58000000) ENGINE = InnoDB,
 PARTITION p29 VALUES LESS THAN (60000000) ENGINE = InnoDB,
 PARTITION p30 VALUES LESS THAN (62000000) ENGINE = InnoDB,
 PARTITION p31 VALUES LESS THAN (64000000) ENGINE = InnoDB,
 PARTITION p32 VALUES LESS THAN (66000000) ENGINE = InnoDB,
 PARTITION p33 VALUES LESS THAN (68000000) ENGINE = InnoDB,
 PARTITION p34 VALUES LESS THAN (70000000) ENGINE = InnoDB,
 PARTITION p35 VALUES LESS THAN (72000000) ENGINE = InnoDB,
 PARTITION p36 VALUES LESS THAN (74000000) ENGINE = InnoDB,
 PARTITION p37 VALUES LESS THAN (76000000) ENGINE = InnoDB,
 PARTITION p38 VALUES LESS THAN (78000000) ENGINE = InnoDB,
 PARTITION p39 VALUES LESS THAN (80000000) ENGINE = InnoDB,
 PARTITION p40 VALUES LESS THAN (82000000) ENGINE = InnoDB,
 PARTITION p41 VALUES LESS THAN (84000000) ENGINE = InnoDB,
 PARTITION p42 VALUES LESS THAN (86000000) ENGINE = InnoDB,
 PARTITION p43 VALUES LESS THAN (88000000) ENGINE = InnoDB,
 PARTITION p44 VALUES LESS THAN (90000000) ENGINE = InnoDB,
 PARTITION p45 VALUES LESS THAN (92000000) ENGINE = InnoDB,
 PARTITION p46 VALUES LESS THAN (94000000) ENGINE = InnoDB,
 PARTITION p47 VALUES LESS THAN (96000000) ENGINE = InnoDB,
 PARTITION p48 VALUES LESS THAN (98000000) ENGINE = InnoDB,
 PARTITION p49 VALUES LESS THAN (100000000) ENGINE = InnoDB,
 PARTITION p50 VALUES LESS THAN (102000000) ENGINE = InnoDB,
 PARTITION p51 VALUES LESS THAN (104000000) ENGINE = InnoDB,
 PARTITION p52 VALUES LESS THAN (106000000) ENGINE = InnoDB,
 PARTITION p53 VALUES LESS THAN (108000000) ENGINE = InnoDB,
 PARTITION p54 VALUES LESS THAN (110000000) ENGINE = InnoDB,
 PARTITION p55 VALUES LESS THAN (112000000) ENGINE = InnoDB,
 PARTITION p56 VALUES LESS THAN (114000000) ENGINE = InnoDB,
 PARTITION p57 VALUES LESS THAN (116000000) ENGINE = InnoDB,
 PARTITION p58 VALUES LESS THAN (118000000) ENGINE = InnoDB,
 PARTITION p59 VALUES LESS THAN (120000000) ENGINE = InnoDB,
 PARTITION p60 VALUES LESS THAN (122000000) ENGINE = InnoDB,
 PARTITION p61 VALUES LESS THAN (124000000) ENGINE = InnoDB,
 PARTITION p62 VALUES LESS THAN (126000000) ENGINE = InnoDB,
 PARTITION p63 VALUES LESS THAN (128000000) ENGINE = InnoDB,
 PARTITION p64 VALUES LESS THAN (130000000) ENGINE = InnoDB,
 PARTITION p65 VALUES LESS THAN (132000000) ENGINE = InnoDB,
 PARTITION p66 VALUES LESS THAN (134000000) ENGINE = InnoDB,
 PARTITION p67 VALUES LESS THAN (136000000) ENGINE = InnoDB,
 PARTITION p68 VALUES LESS THAN (138000000) ENGINE = InnoDB,
 PARTITION p69 VALUES LESS THAN (140000000) ENGINE = InnoDB,
 PARTITION p70 VALUES LESS THAN (142000000) ENGINE = InnoDB,
 PARTITION p71 VALUES LESS THAN (144000000) ENGINE = InnoDB,
 PARTITION p72 VALUES LESS THAN (146000000) ENGINE = InnoDB,
 PARTITION p73 VALUES LESS THAN (148000000) ENGINE = InnoDB,
 PARTITION p74 VALUES LESS THAN (150000000) ENGINE = InnoDB,
 PARTITION p75 VALUES LESS THAN (152000000) ENGINE = InnoDB,
 PARTITION p76 VALUES LESS THAN (154000000) ENGINE = InnoDB,
 PARTITION p77 VALUES LESS THAN (156000000) ENGINE = InnoDB,
 PARTITION p78 VALUES LESS THAN (158000000) ENGINE = InnoDB,
 PARTITION p79 VALUES LESS THAN (160000000) ENGINE = InnoDB,
 PARTITION p80 VALUES LESS THAN (162000000) ENGINE = InnoDB,
 PARTITION p81 VALUES LESS THAN (164000000) ENGINE = InnoDB,
 PARTITION p82 VALUES LESS THAN (166000000) ENGINE = InnoDB,
 PARTITION p83 VALUES LESS THAN (168000000) ENGINE = InnoDB,
 PARTITION p84 VALUES LESS THAN (170000000) ENGINE = InnoDB,
 PARTITION p85 VALUES LESS THAN (172000000) ENGINE = InnoDB,
 PARTITION p86 VALUES LESS THAN (174000000) ENGINE = InnoDB,
 PARTITION p87 VALUES LESS THAN (176000000) ENGINE = InnoDB,
 PARTITION p88 VALUES LESS THAN (178000000) ENGINE = InnoDB,
 PARTITION p89 VALUES LESS THAN (180000000) ENGINE = InnoDB,
 PARTITION p90 VALUES LESS THAN (182000000) ENGINE = InnoDB,
 PARTITION p91 VALUES LESS THAN (184000000) ENGINE = InnoDB,
 PARTITION p92 VALUES LESS THAN (186000000) ENGINE = InnoDB,
 PARTITION p93 VALUES LESS THAN (188000000) ENGINE = InnoDB,
 PARTITION p94 VALUES LESS THAN (190000000) ENGINE = InnoDB,
 PARTITION p95 VALUES LESS THAN (192000000) ENGINE = InnoDB,
 PARTITION p96 VALUES LESS THAN (194000000) ENGINE = InnoDB,
 PARTITION p97 VALUES LESS THAN (196000000) ENGINE = InnoDB,
 PARTITION p98 VALUES LESS THAN (198000000) ENGINE = InnoDB,
 PARTITION p99 VALUES LESS THAN (200000000) ENGINE = InnoDB,
 PARTITION p100 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Records of bc_transaction
-- ----------------------------
