/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : yuxiang_edu_vod

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/12/2020 19:19:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vod_live_category
-- ----------------------------
DROP TABLE IF EXISTS `vod_live_category`;
CREATE TABLE `vod_live_category`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(5) NULL DEFAULT 0 COMMENT '父id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '直播分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vod_live_category
-- ----------------------------
INSERT INTO `vod_live_category` VALUES (10, 0, '编程语言', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (11, 10, 'Java', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (12, 10, 'Golang', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (13, 10, 'Python', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (14, 10, 'C++', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (15, 0, 'Spring框架', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (16, 15, 'Spring', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (17, 15, 'Spring Boot', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (18, 15, 'Spring Cloud', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (19, 15, 'Spirng Security', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (20, 15, 'Spring Data', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (21, 0, '前端开发', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (22, 21, 'Html+CSS', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (23, 21, 'JavaScript', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (24, 21, 'Vue.js', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (25, 21, 'Node.js', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (26, 0, '中间件', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (27, 26, 'RabbitMQ', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (28, 26, 'MyCat', '2020-11-23 13:48:59', '2020-11-23 13:48:59');
INSERT INTO `vod_live_category` VALUES (29, 0, '搜索引擎', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (30, 29, 'Elasticsearch', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (31, 0, '大数据', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (32, 0, '云开发', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (33, 0, '小程序', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (34, 33, '微信小程序', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (35, 33, '支付宝小程序', '2020-11-23 13:49:00', '2020-11-23 13:49:00');
INSERT INTO `vod_live_category` VALUES (36, 31, 'Hadoop', '2020-11-23 14:09:10', '2020-11-23 14:09:10');
INSERT INTO `vod_live_category` VALUES (37, 32, 'serverless入门', '2020-11-23 14:09:10', '2020-11-23 14:09:10');
INSERT INTO `vod_live_category` VALUES (38, 31, 'Kafka', '2020-11-23 14:09:10', '2020-11-23 14:09:10');

-- ----------------------------
-- Table structure for vod_live_info
-- ----------------------------
DROP TABLE IF EXISTS `vod_live_info`;
CREATE TABLE `vod_live_info`  (
  `id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `tid` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲师id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直播标题',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直播封面',
  `category_id` int(5) NULL DEFAULT NULL COMMENT '直播分类id',
  `category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直播分类名称',
  `introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人简介',
  `is_disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '直播信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vod_live_info
-- ----------------------------
INSERT INTO `vod_live_info` VALUES ('1329065821762281473', '20184141090', 'SpringBoot 呦呦呦', 'string', 17, 'SpringBoot', 'SpringBoot Introduction', 0, 0, '2020-11-18 22:15:59', '2020-11-28 16:34:50');
INSERT INTO `vod_live_info` VALUES ('1329325597746393090', '20184141091', 'SpringBoot 阿拉拉了', 'string', 17, 'SpringBoot', 'SpringBoot Introduction', 0, 0, '2020-11-19 15:28:15', '2020-11-28 16:35:16');

SET FOREIGN_KEY_CHECKS = 1;
