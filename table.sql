/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50729
Source Host           : 127.0.0.1:3306
Source Database       : stomp

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-11-03 20:05:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `workspace_id` int(11) DEFAULT NULL COMMENT '工作空间id',
                            `doc_name` varchar(20) DEFAULT NULL,
                            `type` varchar(10) DEFAULT NULL COMMENT '文档类型 DOC PPT',
                            `state` tinyint(1) DEFAULT '1' COMMENT '状态 1:正常 0:删除',
                            `pwd` varchar(128) DEFAULT NULL COMMENT '文档密码',
                            `hidden` bit(1) DEFAULT b'0' COMMENT '是否隐藏 true:隐藏 false:显示',
                            `sort` smallint(4) DEFAULT NULL COMMENT '排序规则',
                            `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档信息表';

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
                        `id` int(11) NOT NULL,
                        `document_id` int(11) DEFAULT NULL COMMENT '文档id',
                        `workspace_id` int(11) DEFAULT NULL COMMENT '文档所属空间id',
                        `content` longtext COMMENT '内容信息',
                        `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档页信息';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `mobile` char(11) DEFAULT NULL COMMENT '手机号码',
                        `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
                        `pwd` varchar(128) DEFAULT NULL COMMENT '密码',
                        `init_pwd` varchar(128) DEFAULT NULL COMMENT '初始化密码',
                        `state` tinyint(1) DEFAULT '1' COMMENT '状态 1:正常 0:锁定',
                        `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for workspace
-- ----------------------------
DROP TABLE IF EXISTS `workspace`;
CREATE TABLE `workspace` (
                             `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `title` varchar(20) DEFAULT NULL COMMENT '命名空间的名称',
                             `notice` varchar(50) DEFAULT NULL COMMENT '命名空间的公告',
                             `user_id` int(11) DEFAULT NULL COMMENT '该工作空间所属的用户id',
                             `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='命名空间';
