/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ti

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-25 17:15:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bc_block
-- ----------------------------
DROP TABLE IF EXISTS `bc_block`;
CREATE TABLE `bc_block` (
  `hash` varchar(100) NOT NULL,
  `number` bigint(20) NOT NULL,
  `parent_hash` varchar(100) NOT NULL,
  `coinbase` varchar(100) NOT NULL,
  `difficulty` bigint(20) NOT NULL,
  `gas_limit` decimal(50,0) NOT NULL,
  `gas_used` decimal(50,0) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`hash`),
  KEY `number` (`number`),
  KEY `coinbase` (`coinbase`),
  KEY `hash` (`hash`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_block
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
  `token_address` varchar(100) NOT NULL,
  `token_name` varchar(50) NOT NULL,
  `symbol` varchar(50) NOT NULL,
  `total_supply` decimal(50,0) NOT NULL,
  `decimals` bigint(20) NOT NULL DEFAULT '0',
  `holders` bigint(20) NOT NULL DEFAULT '0' COMMENT '账户数量',
  `transfers` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易次数',
  PRIMARY KEY (`token_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_erc20_token
-- ----------------------------

-- ----------------------------
-- Table structure for bc_erc20_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_erc20_transaction`;
CREATE TABLE `bc_erc20_transaction` (
  `tx_hash` varchar(100) NOT NULL,
  `token_address` varchar(100) NOT NULL,
  `block_hash` varchar(100) NOT NULL,
  `block_number` bigint(20) NOT NULL,
  `gas` decimal(50,0) NOT NULL,
  `gas_price` decimal(50,0) NOT NULL,
  `send_address` varchar(100) NOT NULL,
  `receive_address` varchar(100) NOT NULL DEFAULT '',
  `value` decimal(50,0) unsigned NOT NULL DEFAULT '0',
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`tx_hash`),
  KEY `tx_hash` (`tx_hash`),
  KEY `token_address` (`token_address`),
  KEY `block_hash` (`block_hash`),
  KEY `block_number` (`block_number`),
  KEY `send_address` (`send_address`),
  KEY `receive_address` (`receive_address`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_erc20_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for bc_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_transaction`;
CREATE TABLE `bc_transaction` (
  `hash` varchar(100) NOT NULL,
  `block_hash` varchar(100) NOT NULL,
  `block_number` bigint(20) NOT NULL,
  `gas` decimal(50,0) unsigned NOT NULL,
  `gas_price` decimal(50,0) unsigned NOT NULL,
  `send_address` varchar(100) NOT NULL,
  `receive_address` varchar(100) NOT NULL DEFAULT '',
  `value` decimal(50,0) NOT NULL DEFAULT '0',
  `data` text NOT NULL,
  `timestamp` datetime NOT NULL,
  `tx_type` varchar(20) NOT NULL DEFAULT '1' COMMENT '交易类型：1、以太币交易；2、合约调用；3、创建合约；',
  PRIMARY KEY (`hash`),
  KEY `hash` (`hash`),
  KEY `block_hash` (`block_hash`),
  KEY `block_number` (`block_number`),
  KEY `send_address` (`send_address`),
  KEY `receive_address` (`receive_address`),
  KEY `tx_type` (`tx_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_transaction
-- ----------------------------
