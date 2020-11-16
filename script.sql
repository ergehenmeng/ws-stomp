/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 127.0.0.1:3306
 Source Schema         : stomp

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 16/11/2020 21:40:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document`  (
                             `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `space_id` bigint(20) NULL DEFAULT NULL COMMENT '工作空间id',
                             `doc_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档名称',
                             `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档类型 DOC PPT',
                             `state` tinyint(1) NULL DEFAULT 1 COMMENT '状态 1:正常 0:删除',
                             `pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档密码',
                             `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档内容',
                             `hidden` bit(1) NULL DEFAULT b'0' COMMENT '文件隐藏状态 true:隐藏 false:正常',
                             `user_id` bigint(20) NULL DEFAULT NULL COMMENT '文档所属用户',
                             `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
                             `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 338730027934343169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文档信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES (1, 1, '我个是文档', 'WORD', 1, NULL, NULL, b'0', NULL, '2020-11-12 21:08:23', '2020-11-14 14:59:44');
INSERT INTO `document` VALUES (2, 1, '我是个MD', 'MD', 1, NULL, NULL, b'0', NULL, '2020-11-14 14:59:55', '2020-11-14 14:59:58');
INSERT INTO `document` VALUES (338730027934343168, 1, '二哥胜多负少', 'PPT', 1, NULL, NULL, b'0', 1, '2020-11-14 17:12:11', '2020-11-14 17:12:11');

-- ----------------------------
-- Table structure for space
-- ----------------------------
DROP TABLE IF EXISTS `space`;
CREATE TABLE `space`  (
                          `id` bigint(30) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命名空间的名称',
                          `notice` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命名空间的公告',
                          `user_id` int(20) NULL DEFAULT NULL COMMENT '该工作空间所属的用户id',
                          `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
                          `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '命名空间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of space
-- ----------------------------
INSERT INTO `space` VALUES (1, '我是个工作空间', '近期不做调整', 1, '2020-11-12 21:08:11', '2020-11-12 21:08:11');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint(30) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `mobile` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
                         `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                         `pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                         `init_pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '初始化密码',
                         `state` tinyint(1) NULL DEFAULT 1 COMMENT '状态 1:正常 0:锁定',
                         `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '新增时间',
                         `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13000000000', '二哥很猛', '$2a$10$vtgxvk5DXGbxAy76ChlSCOgdp2ED6yHkfBZRNW8/KhoEVDdXboNLK', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-11-09 20:14:16', '2020-11-12 19:52:05');
INSERT INTO `user` VALUES (2, '13100000000', '殿小二', '$2a$10$vtgxvk5DXGbxAy76ChlSCOgdp2ED6yHkfBZRNW8/KhoEVDdXboNLK', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-11-09 20:14:53', '2020-11-12 19:52:08');

-- ----------------------------
-- Table structure for user_join_space
-- ----------------------------
DROP TABLE IF EXISTS `user_join_space`;
CREATE TABLE `user_join_space`  (
                                    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `user_id` bigint(30) NULL DEFAULT NULL COMMENT 'userId',
                                    `space_id` bigint(20) NULL DEFAULT NULL COMMENT '工作空间id',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_join_space
-- ----------------------------
INSERT INTO `user_join_space` VALUES (1, 1, 1);
INSERT INTO `user_join_space` VALUES (2, 2, 1);

SET FOREIGN_KEY_CHECKS = 1;
