/*
 Navicat MySQL Data Transfer

 Source Server         : hwsql
 Source Server Type    : MySQL
 Source Server Version : 50639
 Source Host           : 192.168.0.126:8635
 Source Schema         : ti

 Target Server Type    : MySQL
 Target Server Version : 50639
 File Encoding         : 65001

 Date: 31/05/2018 21:45:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bc_block
-- ----------------------------
DROP TABLE IF EXISTS `bc_block`;
CREATE TABLE `bc_block`  (
  `hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `number` bigint(20) NOT NULL,
  `parent_hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coinbase` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `difficulty` bigint(20) NOT NULL,
  `gas_limit` decimal(50, 0) NOT NULL,
  `gas_used` decimal(50, 0) NOT NULL,
  `timestamp` datetime(0) NOT NULL,
  PRIMARY KEY (`hash`) USING BTREE,
  INDEX `number`(`number`) USING BTREE,
  INDEX `coinbase`(`coinbase`) USING BTREE,
  INDEX `hash`(`hash`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bc_current_block
-- ----------------------------
DROP TABLE IF EXISTS `bc_current_block`;
CREATE TABLE `bc_current_block`  (
  `bc_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ETH',
  `block_number` bigint(20) NOT NULL DEFAULT 0 COMMENT '记录当前已处理完成的block，仅仅更新，仅一条记录',
  PRIMARY KEY (`bc_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bc_erc20_token
-- ----------------------------
DROP TABLE IF EXISTS `bc_erc20_token`;
CREATE TABLE `bc_erc20_token`  (
  `token_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `symbol` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_supply` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `decimals` bigint(20) NOT NULL DEFAULT 0,
  `holders` bigint(20) NOT NULL DEFAULT 0 COMMENT '账户数量',
  `transfers` bigint(20) NOT NULL DEFAULT 0 COMMENT '交易次数',
  PRIMARY KEY (`token_address`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bc_erc20_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_erc20_transaction`;
CREATE TABLE `bc_erc20_transaction`  (
  `tx_hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `block_hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `block_number` bigint(20) NOT NULL,
  `gas` decimal(50, 0) NOT NULL,
  `gas_price` decimal(50, 0) NOT NULL,
  `send_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receive_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `value` decimal(50, 0) UNSIGNED NOT NULL DEFAULT 0,
  `timestamp` datetime(0) NOT NULL,
  PRIMARY KEY (`tx_hash`) USING BTREE,
  INDEX `tx_hash`(`tx_hash`) USING BTREE,
  INDEX `token_address`(`token_address`) USING BTREE,
  INDEX `block_hash`(`block_hash`) USING BTREE,
  INDEX `block_number`(`block_number`) USING BTREE,
  INDEX `send_address`(`send_address`) USING BTREE,
  INDEX `receive_address`(`receive_address`) USING BTREE,
  INDEX `timestamp`(`timestamp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bc_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bc_transaction`;
CREATE TABLE `bc_transaction`  (
  `hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `block_hash` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `block_number` bigint(20) NOT NULL,
  `gas` decimal(50, 0) UNSIGNED NOT NULL,
  `gas_price` decimal(50, 0) UNSIGNED NOT NULL,
  `send_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receive_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `value` decimal(50, 0) NOT NULL DEFAULT 0,
  `data` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timestamp` datetime(0) NOT NULL,
  `tx_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '交易类型：1、以太币交易；2、合约调用；3、创建合约；',
  PRIMARY KEY (`hash`) USING BTREE,
  INDEX `hash`(`hash`) USING BTREE,
  INDEX `block_hash`(`block_hash`) USING BTREE,
  INDEX `block_number`(`block_number`) USING BTREE,
  INDEX `send_address`(`send_address`) USING BTREE,
  INDEX `receive_address`(`receive_address`) USING BTREE,
  INDEX `tx_type`(`tx_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
