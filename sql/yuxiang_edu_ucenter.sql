/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : yuxiang_edu_ucenter

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/12/2020 19:19:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ucenter_member
-- ----------------------------
DROP TABLE IF EXISTS `ucenter_member`;
CREATE TABLE `ucenter_member`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `wx_openid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `wb_openid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阿里openid',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `mail` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(2) UNSIGNED NULL DEFAULT NULL COMMENT '性别 1 男，2 女',
  `age` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ucenter_member
-- ----------------------------
INSERT INTO `ucenter_member` VALUES ('1330872970285015042', NULL, NULL, '18339536116', '610232665@qq.com', 'eba69eee2ad0178be8588ab9bf5ef34dd9e7352ab5b9d764d7c714bcdb3518f4bdb4bb5451f0aafdafbd3801569d450f0db3685d82fafbcdadd15ee6d5656e15', 'vip_1rz2t9rxgt', NULL, NULL, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ucenter/2020/11/17/cc9f8b9cce004c4a9ed6df53db9dbd0a.png', NULL, 0, 0, '2020-11-23 21:56:57', '2020-11-23 21:56:57');
INSERT INTO `ucenter_member` VALUES ('1330875457209491457', NULL, NULL, '18339533333', NULL, 'eba69eee2ad0178be8588ab9bf5ef34dd9e7352ab5b9d764d7c714bcdb3518f4bdb4bb5451f0aafdafbd3801569d450f0db3685d82fafbcdadd15ee6d5656e15', '任金博', NULL, NULL, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ucenter/2020/11/17/cc9f8b9cce004c4a9ed6df53db9dbd0a.png', NULL, 0, 0, '2020-11-23 22:06:50', '2020-11-23 22:06:50');
INSERT INTO `ucenter_member` VALUES ('1330875795660464129', NULL, NULL, NULL, '610232666@qq.com', 'eba69eee2ad0178be8588ab9bf5ef34dd9e7352ab5b9d764d7c714bcdb3518f4bdb4bb5451f0aafdafbd3801569d450f0db3685d82fafbcdadd15ee6d5656e15', '张三丰', NULL, NULL, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ucenter/2020/11/17/cc9f8b9cce004c4a9ed6df53db9dbd0a.png', NULL, 0, 0, '2020-11-23 22:08:11', '2020-11-23 22:08:11');
INSERT INTO `ucenter_member` VALUES ('1330876395173306370', NULL, NULL, '18339511123', NULL, 'eba69eee2ad0178be8588ab9bf5ef34dd9e7352ab5b9d764d7c714bcdb3518f4bdb4bb5451f0aafdafbd3801569d450f0db3685d82fafbcdadd15ee6d5656e15', 'vip_ilscj0x7nr', NULL, NULL, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/ucenter/2020/11/17/cc9f8b9cce004c4a9ed6df53db9dbd0a.png', NULL, 0, 0, '2020-11-23 22:10:34', '2020-11-23 22:10:34');
INSERT INTO `ucenter_member` VALUES ('1331867518406500354', NULL, '6046050724', NULL, NULL, NULL, '岁月复缠绵i', NULL, NULL, 'https://tvax3.sinaimg.cn/crop.0.0.1002.1002.180/006BaBZaly8ge85gw0nxmj30ru0ruwfx.jpg?KID=imgbed,tva&Expires=1606387735&ssig=4KzOMOKQgD', NULL, 0, 0, '2020-11-26 15:48:56', '2020-11-26 15:48:56');

SET FOREIGN_KEY_CHECKS = 1;
