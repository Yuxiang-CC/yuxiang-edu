/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : yuxiang_edu_core

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 05/12/2020 14:35:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for core_chapter
-- ----------------------------
DROP TABLE IF EXISTS `core_chapter`;
CREATE TABLE `core_chapter`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '章节ID',
  `course_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程ID',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '章节名称',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '显示排序',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of core_chapter
-- ----------------------------
INSERT INTO `core_chapter` VALUES ('1330501519614717953', '1330499631192248321', '第一章-入门', 1, '2020-11-22 21:20:56', '2020-11-22 21:20:56');
INSERT INTO `core_chapter` VALUES ('1330501576615309313', '1330499631192248321', '第二章-案例', 2, '2020-11-22 21:21:10', '2020-11-22 21:21:10');
INSERT INTO `core_chapter` VALUES ('1330507640828071937', '1330499631192248321', '第三章-源码分析', 3, '2020-11-22 21:45:16', '2020-11-22 21:45:25');
INSERT INTO `core_chapter` VALUES ('1331122593561903105', '1330500687976501249', '第一章-介绍', 1, '2020-11-24 14:28:52', '2020-11-24 14:28:52');
INSERT INTO `core_chapter` VALUES ('1334127589660418049', '1334127515253465089', 'Liunx系统介绍', 1, '2020-12-02 21:29:39', '2020-12-02 21:29:39');
INSERT INTO `core_chapter` VALUES ('1335017776351318017', '1335017748186566657', '第一章 了解编程语言', 1, '2020-12-05 08:26:56', '2020-12-05 08:27:35');
INSERT INTO `core_chapter` VALUES ('1335017887731060738', '1335017748186566657', '第二章 学习Java', 2, '2020-12-05 08:27:23', '2020-12-05 08:27:23');
INSERT INTO `core_chapter` VALUES ('1335023750160084993', '1335023711740260353', '第一章 认识MQ', 1, '2020-12-05 08:50:40', '2020-12-05 08:50:40');
INSERT INTO `core_chapter` VALUES ('1335023782330396674', '1335023711740260353', '第一章 安装MQ', 2, '2020-12-05 08:50:48', '2020-12-05 08:50:48');
INSERT INTO `core_chapter` VALUES ('1335024884912254977', '1335024747305529346', '第一章 基础了解', 1, '2020-12-05 08:55:11', '2020-12-05 08:55:11');
INSERT INTO `core_chapter` VALUES ('1335024924808474625', '1335024747305529346', 'Python 基础语法', 2, '2020-12-05 08:55:20', '2020-12-05 08:55:20');
INSERT INTO `core_chapter` VALUES ('1335026223004282881', '1335026006544642049', '了解权限模型', 1, '2020-12-05 09:00:30', '2020-12-05 09:00:30');
INSERT INTO `core_chapter` VALUES ('1335026254985850882', '1335026006544642049', '设计', 2, '2020-12-05 09:00:37', '2020-12-05 09:00:37');

-- ----------------------------
-- Table structure for core_comment
-- ----------------------------
DROP TABLE IF EXISTS `core_comment`;
CREATE TABLE `core_comment`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '讲师ID',
  `parent_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复的评论id',
  `course_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '课程id',
  `teacher_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '讲师id',
  `member_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE,
  INDEX `idx_member_id`(`member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for core_course
-- ----------------------------
DROP TABLE IF EXISTS `core_course`;
CREATE TABLE `core_course`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程标题',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '销售数量',
  `view_count` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览数量',
  `version` bigint(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_title`(`title`) USING BTREE,
  INDEX `idx_subject_id`(`subject_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of core_course
-- ----------------------------
INSERT INTO `core_course` VALUES ('1330499631192248321', '20184141090', '1330482503588335617', '1330482503512838146', 'SpringBoot训练营', 0.01, 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/02/011090173fa44b5483825e4f11f7ccec.jpg', 0, 63, 1, 'Normal', '2020-11-22 21:13:26', '2020-12-05 09:14:07');
INSERT INTO `core_course` VALUES ('1330500687976501249', '20184141090', '1330483477350486017', '1330482503743524865', '雨伞学院训练营', 0.00, 2, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/02/e62f9656fbcf44ccb26275f5a4809c39.jpg', 0, 15, 1, 'Normal', '2020-11-22 21:17:38', '2020-12-02 21:34:01');
INSERT INTO `core_course` VALUES ('1334127515253465089', '1334102594850656257', '1330483477350486230', '1330483477350486120', 'Liunx高级', 0.01, 15, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/02/f97ee6aa76be402ea861235f0efa4698.jpg', 0, 11, 1, 'Normal', '2020-12-02 21:29:21', '2020-12-03 09:01:25');
INSERT INTO `core_course` VALUES ('1335017748186566657', '1334104206302253058', '1330482503588335617', '1330482503512838146', 'Java从基础到实战', 0.99, 5, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/05/2e73f90f6af6432299b6f813ecc948e8.jpg', 0, 0, 1, 'Normal', '2020-12-05 08:26:49', '2020-12-05 08:28:18');
INSERT INTO `core_course` VALUES ('1335023711740260353', '1334104206302253058', '1335020089358979073', '1335020089308647426', 'ActiveMQ 雨巷学院精品课程', 0.00, 20, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/05/fb17fd086f1144c7969a5f948cb43b61.jpg', 0, 0, 1, 'Normal', '2020-12-05 08:50:31', '2020-12-05 08:53:05');
INSERT INTO `core_course` VALUES ('1335024747305529346', '1334103155167727618', '1330482503688998913', '1330482503512838146', '机器学习与电商实战', 0.01, 3, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/05/80277f9de33f4db8aa6ebf552000e65c.jpg', 0, 1, 1, 'Normal', '2020-12-05 08:54:38', '2020-12-05 09:14:13');
INSERT INTO `core_course` VALUES ('1335026006544642049', '1334102594850656257', '1330482503588335617', '1330482503512838146', 'RBAC权限模型', 1.00, 5, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/cover/2020/12/05/3e47f8589e2540f286dc30a651499975.jpg', 0, 0, 1, 'Normal', '2020-12-05 08:59:38', '2020-12-05 09:02:02');

-- ----------------------------
-- Table structure for core_course_collect
-- ----------------------------
DROP TABLE IF EXISTS `core_course_collect`;
CREATE TABLE `core_course_collect`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏ID',
  `course_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程收藏' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for core_course_description
-- ----------------------------
DROP TABLE IF EXISTS `core_course_description`;
CREATE TABLE `core_course_description`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程ID',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程简介',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程简介' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of core_course_description
-- ----------------------------
INSERT INTO `core_course_description` VALUES ('1330499631192248321', '<p>SpringBoot课程简介</p>', '2020-11-22 21:13:26', '2020-12-02 21:33:00');
INSERT INTO `core_course_description` VALUES ('1330500687976501249', '<p>雨伞学院第一次开课</p>\n<p>内容有：</p>\n<ul>\n<li>vue.js的入门</li>\n<li>vue.js的实现原理</li>\n<li>vue.js的组件开发</li>\n</ul>', '2020-11-22 21:17:38', '2020-12-02 21:34:01');
INSERT INTO `core_course_description` VALUES ('1334127515253465089', '<p>Liunx高级</p>\n<ol>\n<li>高级命令</li>\n<li>shell编程</li>\n<li>运维指南</li>\n</ol>', '2020-12-02 21:29:21', '2020-12-02 21:29:21');
INSERT INTO `core_course_description` VALUES ('1335017748186566657', '<p><strong>课程目录：</strong></p>\n<p>第一章：</p>\n<ul>\n<li>了解编程。</li>\n<li>认识Java。</li>\n<li>第一个Java程序。</li>\n</ul>\n<p>第二章：</p>\n<ul>\n<li>安装Java环境。</li>\n</ul>', '2020-12-05 08:26:49', '2020-12-05 08:26:49');
INSERT INTO `core_course_description` VALUES ('1335023711740260353', '<p>精品课程</p>\n<blockquote>\n<p>你值得拥有！！！！</p>\n</blockquote>', '2020-12-05 08:50:31', '2020-12-05 08:50:31');
INSERT INTO `core_course_description` VALUES ('1335024747305529346', '<p>机器学习与电商实战</p>', '2020-12-05 08:54:38', '2020-12-05 08:54:38');
INSERT INTO `core_course_description` VALUES ('1335026006544642049', '<p>RBAC权限模型，课程简介</p>\n<ol>\n<li style=\"text-align: left;\">列表1</li>\n<li style=\"text-align: left;\">列表2</li>\n<li style=\"text-align: left;\">列表3</li>\n</ol>\n<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n<tbody>\n<tr>\n<td style=\"width: 50%;\">表格1</td>\n<td style=\"width: 50%;\">表格2</td>\n</tr>\n<tr>\n<td style=\"width: 50%;\">表格3</td>\n<td style=\"width: 50%;\">表格4</td>\n</tr>\n</tbody>\n</table>\n<p><a href=\"http://yuxiangai.cn\" target=\"_blank\" rel=\"noopener\">雨巷的伞</a>个人博客。</p>', '2020-12-05 08:59:38', '2020-12-05 09:00:00');

-- ----------------------------
-- Table structure for core_subject
-- ----------------------------
DROP TABLE IF EXISTS `core_subject`;
CREATE TABLE `core_subject`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程类别ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称',
  `parent_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序字段',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程科目' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of core_subject
-- ----------------------------
INSERT INTO `core_subject` VALUES ('1330482503512838146', '后端开发', '0', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503588335617', 'Java', '1330482503512838146', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503688998913', 'Python', '1330482503512838146', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503743524865', '前端开发', '0', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503802245121', 'HTML/CSS', '1330482503743524865', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503894519810', 'JavaScript', '1330482503743524865', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503944851458', '编程语言', '0', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330482503995183106', 'Java', '1330482503944851458', 0, '2020-11-22 20:05:23', '2020-11-22 20:05:23');
INSERT INTO `core_subject` VALUES ('1330483477128187905', 'Python', '1330482503944851458', 0, '2020-11-22 20:09:15', '2020-11-22 20:09:15');
INSERT INTO `core_subject` VALUES ('1330483477241434113', 'Golang', '1330482503944851458', 0, '2020-11-22 20:09:15', '2020-11-22 20:09:15');
INSERT INTO `core_subject` VALUES ('1330483477350486017', 'Vue.js', '1330482503743524865', 0, '2020-11-22 20:09:15', '2020-11-22 20:09:15');
INSERT INTO `core_subject` VALUES ('1330483477350486111', '大数据', '0', 0, '2020-11-26 11:17:50', '2020-11-26 11:17:53');
INSERT INTO `core_subject` VALUES ('1330483477350486120', '运维', '0', 0, '2020-12-02 21:18:38', '2020-12-02 21:18:41');
INSERT INTO `core_subject` VALUES ('1330483477350486230', 'Liunx', '1330483477350486120', 0, '2020-12-02 21:19:04', '2020-12-02 21:19:06');
INSERT INTO `core_subject` VALUES ('1330483477350495221', 'Hadoop', '1330483477350486111', 0, '2020-12-02 21:24:48', '2020-12-02 21:24:50');
INSERT INTO `core_subject` VALUES ('1335020089308647426', '中间件', '0', 0, '2020-12-05 08:36:07', '2020-12-05 08:36:07');
INSERT INTO `core_subject` VALUES ('1335020089358979073', '消息中间件', '1335020089308647426', 0, '2020-12-05 08:36:07', '2020-12-05 08:36:07');
INSERT INTO `core_subject` VALUES ('1335020089426087937', '数据库中间件', '1335020089308647426', 0, '2020-12-05 08:36:07', '2020-12-05 08:36:07');
INSERT INTO `core_subject` VALUES ('1335021503019872258', 'Kafka', '1330483477350486111', 0, '2020-12-05 08:41:44', '2020-12-05 08:41:44');

-- ----------------------------
-- Table structure for core_teacher
-- ----------------------------
DROP TABLE IF EXISTS `core_teacher`;
CREATE TABLE `core_teacher`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '讲师ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int(10) UNSIGNED NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `join_date` date NOT NULL COMMENT '入驻时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '讲师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of core_teacher
-- ----------------------------
INSERT INTO `core_teacher` VALUES ('1334102163806228481', '刘德华', '曾任美团技术顾问', '211院校', 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/273778f8af7640d79926b962f16d08cc.jpg', 0, '2020-12-02', 0, '2020-12-02 19:48:37', '2020-12-02 19:48:37');
INSERT INTO `core_teacher` VALUES ('1334102594850656257', '胡歌', '从事教育十余年', '拥有数百学生', 2, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/e363ceb4f60f46f081cb2f4c3f7ef632.jpg', 0, '2020-12-02', 0, '2020-12-02 19:50:20', '2020-12-02 19:50:57');
INSERT INTO `core_teacher` VALUES ('1334103155167727618', '周杰伦', '携程架构师', '211硕士毕业', 2, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/09999d553e9041eea66d9e80b4b1d8f9.jpg', 0, '2020-12-02', 0, '2020-12-02 19:52:33', '2020-12-02 19:52:33');
INSERT INTO `core_teacher` VALUES ('1334103516150501378', '钟汉良', '啦啦啦开源项目发起人', '985院校毕业生', 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/795c2255963d482bb7bfbb5f64e83c44.jpg', 0, '2020-12-02', 0, '2020-12-02 19:53:59', '2020-12-02 19:53:59');
INSERT INTO `core_teacher` VALUES ('1334104206302253058', '王力宏', '大意没有闪公司技术合伙人', '著名国外高校毕业生', 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/ac1ec132f8804a849fa36bb34c9c92aa.jpg', 0, '2020-12-02', 0, '2020-12-02 19:56:44', '2020-12-02 19:56:44');
INSERT INTO `core_teacher` VALUES ('1334104925805744130', '陈伟霆', '简介简介简介', '讲师资历', 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/7a09b5ab015e47a4b68e995fae5c8df1.jpg', 0, '2020-12-02', 0, '2020-12-02 19:59:35', '2020-12-02 19:59:35');
INSERT INTO `core_teacher` VALUES ('20184141090', '任金博', '年纪轻轻，能加班', '新乡学院计算机专业', 1, 'https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/11/22/54d66c3cea224275a5373168b004e165.png', 1, '2020-11-22', 0, '2020-11-22 17:35:07', '2020-11-22 19:36:58');

-- ----------------------------
-- Table structure for core_video
-- ----------------------------
DROP TABLE IF EXISTS `core_video`;
CREATE TABLE `core_video`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频ID',
  `course_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '章节ID',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始文件名称',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序字段',
  `play_count` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '播放次数',
  `is_free` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT 0 COMMENT '视频时长（秒）',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'Empty' COMMENT '状态',
  `size` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '视频源文件大小（字节）',
  `version` bigint(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_chapter_id`(`chapter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程视频' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of core_video
-- ----------------------------
INSERT INTO `core_video` VALUES ('1330501685251977217', '1330499631192248321', '1330501519614717953', 'SpringBoot介绍', 'fd7f28614ab84b69a26af3fd8560b54d', '2020-11-21_16-12-37.mp4', 1, 0, 1, 0, 'Empty', 0, 1, '2020-11-22 21:21:36', '2020-11-22 21:31:09');
INSERT INTO `core_video` VALUES ('1330501781205069825', '1330499631192248321', '1330501576615309313', 'SpringBoot-HelloWorld', '68f2b95ac28d4ed2a60f884dbd8fab15', '2020-11-18_09-54-08.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2020-11-22 21:21:59', '2020-11-22 21:35:42');
INSERT INTO `core_video` VALUES ('1330508119146500097', '1330499631192248321', '1330507640828071937', 'SpringBoot自动注入源码', 'c56ec02ad32f4533b8dcfee5f22cc265', '2020-11-21_16-23-42.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2020-11-22 21:47:10', '2020-11-22 21:47:10');
INSERT INTO `core_video` VALUES ('1331122745395707905', '1330500687976501249', '1331122593561903105', 'vue.js介绍', 'c56ec02ad32f4533b8dcfee5f22cc265', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-11-24 14:29:28', '2020-11-24 14:29:28');
INSERT INTO `core_video` VALUES ('1331122888727658498', '1330500687976501249', '1331122593561903105', 'vue.js与其他框架的比较', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 2, 0, 1, 0, 'Empty', 0, 1, '2020-11-24 14:30:02', '2020-11-24 14:30:02');
INSERT INTO `core_video` VALUES ('1334127821961945089', '1334127515253465089', '1334127589660418049', 'Unix认识', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-12-02 21:30:34', '2020-12-02 21:30:34');
INSERT INTO `core_video` VALUES ('1335018025249705986', '1335017748186566657', '1335017776351318017', '第一个Java程序', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 1, 0, 1, 0, 'Empty', 0, 1, '2020-12-05 08:27:55', '2020-12-05 08:27:55');
INSERT INTO `core_video` VALUES ('1335018092006248449', '1335017748186566657', '1335017887731060738', '安装Java环境', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 08:28:11', '2020-12-05 08:28:11');
INSERT INTO `core_video` VALUES ('1335024278604640257', '1335023711740260353', '1335023750160084993', 'JMS认识', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 0, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 08:52:46', '2020-12-05 08:52:46');
INSERT INTO `core_video` VALUES ('1335024342169317378', '1335023711740260353', '1335023782330396674', '下载安装', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 08:53:01', '2020-12-05 08:53:01');
INSERT INTO `core_video` VALUES ('1335024992663924737', '1335024747305529346', '1335024884912254977', '数学基础', 'c56ec02ad32f4533b8dcfee5f22cc265', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 08:55:36', '2020-12-05 08:55:36');
INSERT INTO `core_video` VALUES ('1335025061408567298', '1335024747305529346', '1335024924808474625', 'Python基础语法', 'c56ec02ad32f4533b8dcfee5f22cc265', NULL, 1, 0, 1, 0, 'Empty', 0, 1, '2020-12-05 08:55:53', '2020-12-05 08:55:53');
INSERT INTO `core_video` VALUES ('1335025195978616834', '1335024747305529346', '1335024924808474625', 'Python 高级语法', 'c56ec02ad32f4533b8dcfee5f22cc265', NULL, 2, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 08:56:25', '2020-12-05 08:56:25');
INSERT INTO `core_video` VALUES ('1335026316486930434', '1335026006544642049', '1335026223004282881', '认识权限设计', 'fd7f28614ab84b69a26af3fd8560b54d', NULL, 1, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 09:00:52', '2020-12-05 09:00:52');
INSERT INTO `core_video` VALUES ('1335026354168557570', '1335026006544642049', '1335026254985850882', '数据库设计（上）', 'fd7f28614ab84b69a26af3fd8560b54d', NULL, 1, 0, 1, 0, 'Empty', 0, 1, '2020-12-05 09:01:01', '2020-12-05 09:01:46');
INSERT INTO `core_video` VALUES ('1335026428541956098', '1335026006544642049', '1335026254985850882', '代码设计', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 3, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 09:01:19', '2020-12-05 09:01:30');
INSERT INTO `core_video` VALUES ('1335026574952525826', '1335026006544642049', '1335026254985850882', '数据库设计（下）', '68f2b95ac28d4ed2a60f884dbd8fab15', NULL, 2, 0, 0, 0, 'Empty', 0, 1, '2020-12-05 09:01:54', '2020-12-05 09:01:54');

SET FOREIGN_KEY_CHECKS = 1;
