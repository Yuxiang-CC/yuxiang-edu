/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : yuxiang_edu_ams

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/12/2020 19:17:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ams_ad
-- ----------------------------
DROP TABLE IF EXISTS `ams_ad`;
CREATE TABLE `ams_ad`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `type_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型ID',
  `image_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `color` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景颜色',
  `link_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '链接地址',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`title`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告推荐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ams_ad
-- ----------------------------
INSERT INTO `ams_ad` VALUES ('1330423433787383810', '个人博客', '1330418893629464578', 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ad/2020/11/22/6c68d6002a414a5dae4fab3ffc43f914.png', 'color', 'http://yuxiangai.cn', 0, '2020-11-22 16:10:39', '2020-11-22 16:10:39');
INSERT INTO `ams_ad` VALUES ('1330423582999748610', 'title2', '1330418893629464578', 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ad/2020/11/22/6c68d6002a414a5dae4fab3ffc43f914.png', 'color2', 'linkUrl2', 1, '2020-11-22 16:11:15', '2020-11-22 16:11:15');
INSERT INTO `ams_ad` VALUES ('1330423632744194049', 'title3', '1330418893629464578', 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ad/2020/11/22/6c68d6002a414a5dae4fab3ffc43f914.png', 'color3', 'linkUrl3', 2, '2020-11-22 16:11:27', '2020-11-22 16:11:27');
INSERT INTO `ams_ad` VALUES ('1330423721684410369', 'string', '1330419141168898049', 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ad/2020/11/22/6c68d6002a414a5dae4fab3ffc43f914.png', 'string', 'string', 1, '2020-11-22 16:11:48', '2020-11-22 16:15:48');

-- ----------------------------
-- Table structure for ams_ad_type
-- ----------------------------
DROP TABLE IF EXISTS `ams_ad_type`;
CREATE TABLE `ams_ad_type`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '推荐位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ams_ad_type
-- ----------------------------
INSERT INTO `ams_ad_type` VALUES ('1330418893629464578', '首页推荐', '2020-11-22 15:52:37', '2020-11-22 15:52:37');
INSERT INTO `ams_ad_type` VALUES ('1330419005088899074', '直播页面推荐', '2020-11-22 15:53:03', '2020-11-22 15:55:15');
INSERT INTO `ams_ad_type` VALUES ('1330419141168898049', '课程详情页面推荐', '2020-11-22 15:53:36', '2020-11-22 15:53:36');
INSERT INTO `ams_ad_type` VALUES ('1330487516398104578', '测试推荐位', '2020-11-22 20:25:18', '2020-11-22 20:25:18');

SET FOREIGN_KEY_CHECKS = 1;
