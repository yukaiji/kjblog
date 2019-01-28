/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 127.0.0.1:3306
 Source Schema         : kj_blog

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 28/01/2019 17:10:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `article_class_id` int(10) DEFAULT NULL COMMENT '文章分类id',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文章标题',
  `article_sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文章副标题',
  `article_digest` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '文章摘要',
  `created_time` datetime(0) DEFAULT NULL COMMENT '文章时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_class
-- ----------------------------
DROP TABLE IF EXISTS `article_class`;
CREATE TABLE `article_class`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `article_class_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文章分类名称',
  `article_class_id` int(11) DEFAULT NULL COMMENT '文章分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_detail
-- ----------------------------
DROP TABLE IF EXISTS `article_detail`;
CREATE TABLE `article_detail`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
  `article_detail` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '文章内容',
  `article_import` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '重要提示',
  `article_prev_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上一篇文章标题',
  `article_prev_id` int(10) DEFAULT NULL COMMENT '上一篇文章id',
  `article_next_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '下一篇文章标题',
  `article_next_id` int(10) DEFAULT NULL COMMENT '下一篇文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
