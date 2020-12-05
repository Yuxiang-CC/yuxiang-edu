/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : yuxiang_edu_statistics

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/12/2020 19:17:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for statistics_daily
-- ----------------------------
DROP TABLE IF EXISTS `statistics_daily`;
CREATE TABLE `statistics_daily`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `date_calculated` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统计日期',
  `register_num` int(11) NOT NULL DEFAULT 0 COMMENT '注册人数',
  `login_num` int(11) NOT NULL DEFAULT 0 COMMENT '登录人数',
  `video_view_num` int(11) NOT NULL DEFAULT 0 COMMENT '每日播放视频数',
  `course_num` int(11) NOT NULL DEFAULT 0 COMMENT '每日新增课程数',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `statistics_day`(`date_calculated`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '网站统计日数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistics_daily
-- ----------------------------
INSERT INTO `statistics_daily` VALUES ('1330495232831225857', '2020-11-16', 3, 191, 242, 240, '2020-11-22 20:55:58', '2020-11-22 20:55:58');
INSERT INTO `statistics_daily` VALUES ('1333657370504503298', '2020-11-30', 0, 145, 133, 117, '2020-12-01 14:21:10', '2020-12-01 14:21:10');
INSERT INTO `statistics_daily` VALUES ('1333667797619798018', '2020-11-29', 0, 15, 51, 36, '2020-12-01 15:02:36', '2020-12-01 15:02:36');
INSERT INTO `statistics_daily` VALUES ('1333667820822687746', '2020-11-28', 0, 94, 68, 40, '2020-12-01 15:02:41', '2020-12-01 15:02:41');
INSERT INTO `statistics_daily` VALUES ('1333667839109853185', '2020-11-26', 1, 90, 48, 29, '2020-12-01 15:02:46', '2020-12-01 15:02:46');
INSERT INTO `statistics_daily` VALUES ('1333667852401602562', '2020-12-01', 0, 79, 36, 25, '2020-12-01 15:02:49', '2020-12-01 15:02:49');

SET FOREIGN_KEY_CHECKS = 1;
