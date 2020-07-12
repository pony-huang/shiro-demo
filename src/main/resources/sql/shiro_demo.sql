/*
Navicat MySQL Data Transfer

Source Server         : DESKTOP-RD3SK04_MYSQL
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : shiro_demo

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2020-07-12 22:47:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acl_log
-- ----------------------------
DROP TABLE IF EXISTS `acl_log`;
CREATE TABLE `acl_log` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `operation` varchar(255) NOT NULL,
  `uri` varchar(255) NOT NULL DEFAULT '' COMMENT '操作uri',
  `host` varchar(255) NOT NULL,
  `device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `gmt_create` date NOT NULL,
  `gmt_modified` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志';

-- ----------------------------
-- Records of acl_log
-- ----------------------------

-- ----------------------------
-- Table structure for acl_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_permission`;
CREATE TABLE `acl_permission` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';

-- ----------------------------
-- Records of acl_permission
-- ----------------------------
INSERT INTO `acl_permission` VALUES ('1195268474480156673', '-1', '系统管理', '0', 'admin', '/admin', 'Layout', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195268616021139457', '1195268474480156673', '用户管理', '1', null, 'user/list', '/admin/user/list', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195268788138598401', '1195268474480156673', '角色管理', '1', null, 'role/list', '/admin/role/list', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195268893830864898', '1195268474480156673', '菜单管理', '1', null, 'menu/list', '/admin/menu/list', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269143060602882', '1195268616021139457', '查看', '2', 'user:list', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269295926206466', '1195268616021139457', '添加', '2', 'user:add', 'user/add', '/admin/user/form', 'icon', null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269473479483394', '1195268616021139457', '修改', '2', 'user:update', 'user/update/:id', '/admin/user/form', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269547269873666', '1195268616021139457', '删除', '2', 'user:remove', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269821262782465', '1195268788138598401', '修改', '2', 'role:update', 'role/update/:id', '/admin/role/form', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195269903542444034', '1195268788138598401', '查看', '2', 'role:list', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270037005197313', '1195268788138598401', '添加', '2', 'role:add', 'role/add', '/admin/role/form', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270442602782721', '1195268788138598401', '删除', '2', 'role:remove', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270621548568578', '1195268788138598401', '角色权限', '2', 'role:acl', 'role/distribution/:id', '/admin/role/roleForm', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270744097742849', '1195268893830864898', '查看', '2', 'permission:list', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270810560684034', '1195268893830864898', '添加', '2', 'permission:add', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270862100291586', '1195268893830864898', '修改', '2', 'permission:update', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1195270887933009922', '1195268893830864898', '删除', '2', 'permission:remove', '', '', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_permission` VALUES ('1196301740985311234', '1195268616021139457', '分配角色', '2', 'user:assign', 'user/role/:id', '/admin/user/roleForm', null, null, '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');

-- ----------------------------
-- Table structure for acl_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acl_role
-- ----------------------------
INSERT INTO `acl_role` VALUES ('1', 'Super Admin', '9527', '0', '2020-06-06 00:00:00', '2020-07-06 22:46:42', 'Super Admin');
INSERT INTO `acl_role` VALUES ('1277140274224562177', '无敌管理员', '8888', '0', '2020-06-06 00:00:00', '2020-07-12 22:32:43', '无敌管理员');
INSERT INTO `acl_role` VALUES ('1277140395691606018', '超级管理员', '1111', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55', '超级管理员');
INSERT INTO `acl_role` VALUES ('1277140428734332929', '只有菜单管理', '32323', '0', '2020-06-06 00:00:00', '2020-07-12 22:37:48', '只有菜单管理角色');
INSERT INTO `acl_role` VALUES ('1277140458945904641', '查看管理员', '1235', '0', '2020-06-06 00:00:00', '2020-07-06 22:45:39', '只有查看管理员');

-- ----------------------------
-- Table structure for acl_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission` (
  `id` char(19) NOT NULL DEFAULT '',
  `role_id` char(19) NOT NULL DEFAULT '',
  `permission_id` char(19) NOT NULL DEFAULT '',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Records of acl_role_permission
-- ----------------------------
INSERT INTO `acl_role_permission` VALUES ('1277142670325592065', '1277140395691606018', '1195268788138598401', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277142670325592066', '1277140395691606018', '1195269821262782465', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277142670325592067', '1277140395691606018', '1195269903542444034', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277142670325592068', '1277140395691606018', '1195270037005197313', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277142670325592069', '1277140395691606018', '1195270442602782721', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277142670325592070', '1277140395691606018', '1195270621548568578', '0', '2020-06-06 00:00:00', '2020-06-28 15:31:55');
INSERT INTO `acl_role_permission` VALUES ('1277784216301494273', '1277140274224562177', '1195269143060602882', '1', '2020-06-06 00:00:00', '2020-06-30 10:01:12');
INSERT INTO `acl_role_permission` VALUES ('1277797078612873217', '1277140428734332929', '1195269473479483394', '1', '2020-06-06 00:00:00', '2020-06-30 10:52:19');
INSERT INTO `acl_role_permission` VALUES ('1280150922734899201', '1277140458945904641', '1195269143060602882', '0', '2020-07-06 22:45:39', '2020-07-06 22:45:39');
INSERT INTO `acl_role_permission` VALUES ('1280150922734899202', '1277140458945904641', '1195269903542444034', '0', '2020-07-06 22:45:39', '2020-07-06 22:45:39');
INSERT INTO `acl_role_permission` VALUES ('1280150922734899203', '1277140458945904641', '1195270744097742849', '0', '2020-07-06 22:45:39', '2020-07-06 22:45:39');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824705', '1', '1195268474480156673', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824706', '1', '1195268616021139457', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824707', '1', '1195269143060602882', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824708', '1', '1195269295926206466', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824709', '1', '1195269473479483394', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824710', '1', '1195269547269873666', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824711', '1', '1196301740985311234', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824712', '1', '1195268788138598401', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824713', '1', '1195269821262782465', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824714', '1', '1195269903542444034', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824715', '1', '1195270037005197313', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824716', '1', '1195270442602782721', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824717', '1', '1195270621548568578', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824718', '1', '1195268893830864898', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824719', '1', '1195270744097742849', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824720', '1', '1195270810560684034', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824721', '1', '1195270862100291586', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1280151188762824722', '1', '1195270887933009922', '0', '2020-07-06 22:46:42', '2020-07-06 22:46:42');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879169', '1277140274224562177', '1195268616021139457', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879170', '1277140274224562177', '1195269143060602882', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879171', '1277140274224562177', '1195269295926206466', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879172', '1277140274224562177', '1195269473479483394', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879173', '1277140274224562177', '1195269547269873666', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282318206047879174', '1277140274224562177', '1196301740985311234', '1', '2020-07-12 22:17:39', '2020-07-12 22:17:39');
INSERT INTO `acl_role_permission` VALUES ('1282321997610631170', '1277140274224562177', '1195269143060602882', '0', '2020-07-12 22:32:43', '2020-07-12 22:32:43');
INSERT INTO `acl_role_permission` VALUES ('1282321997610631171', '1277140274224562177', '1195269295926206466', '0', '2020-07-12 22:32:43', '2020-07-12 22:32:43');
INSERT INTO `acl_role_permission` VALUES ('1282321997610631172', '1277140274224562177', '1195269473479483394', '0', '2020-07-12 22:32:43', '2020-07-12 22:32:43');
INSERT INTO `acl_role_permission` VALUES ('1282321997610631173', '1277140274224562177', '1195269547269873666', '0', '2020-07-12 22:32:43', '2020-07-12 22:32:43');
INSERT INTO `acl_role_permission` VALUES ('1282323274952359937', '1277140428734332929', '1195268893830864898', '0', '2020-07-12 22:37:48', '2020-07-12 22:37:48');
INSERT INTO `acl_role_permission` VALUES ('1282323275027857409', '1277140428734332929', '1195270744097742849', '0', '2020-07-12 22:37:48', '2020-07-12 22:37:48');
INSERT INTO `acl_role_permission` VALUES ('1282323275027857410', '1277140428734332929', '1195270810560684034', '0', '2020-07-12 22:37:48', '2020-07-12 22:37:48');
INSERT INTO `acl_role_permission` VALUES ('1282323275027857411', '1277140428734332929', '1195270862100291586', '0', '2020-07-12 22:37:48', '2020-07-12 22:37:48');
INSERT INTO `acl_role_permission` VALUES ('1282323275027857412', '1277140428734332929', '1195270887933009922', '0', '2020-07-12 22:37:48', '2020-07-12 22:37:48');

-- ----------------------------
-- Table structure for acl_user
-- ----------------------------
DROP TABLE IF EXISTS `acl_user`;
CREATE TABLE `acl_user` (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '会员id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信openid',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT '' COMMENT '用户头像',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '盐值',
  `token` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `state` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '账号状态, 0 (默认) 可用，1禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of acl_user
-- ----------------------------
INSERT INTO `acl_user` VALUES ('1', 'admin', '49d2369696bf3fb8f3c413879a335a1a', 'admin', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '7c6100863f1c41f1a7cb7c62f47cf810', null, '0', '2020-06-06 00:00:00', '2020-06-27 21:50:26', '0');
INSERT INTO `acl_user` VALUES ('1276705571495489538', 'spkieadmin', '49d2369696bf3fb8f3c413879a335a1a', '史派克', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '7c6100863f1c41f1a7cb7c62f47cf810', null, '0', '2020-06-06 00:00:00', '2020-07-12 22:19:47', '0');
INSERT INTO `acl_user` VALUES ('1277795741468758017', 'ponyadmin', '8bb2ee652f2e34d84401a2496e24ad6b', 'pony哥', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'c17800227c0e4df2ad4f71aa99a4e4c6', null, '0', '2020-06-06 00:00:00', '2020-07-12 22:31:36', '0');
INSERT INTO `acl_user` VALUES ('1282321035399651330', 'haha', '0571b726af72fd344a5e5996c36d1a86', '哈哈-1', '', '263c94addad44b769d15e331716bc171', null, '1', '2020-07-12 22:28:54', '2020-07-12 22:30:53', '0');
INSERT INTO `acl_user` VALUES ('3', 'ponking', '49d2369696bf3fb8f3c413879a335a1a', 'ponking', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '7c6100863f1c41f1a7cb7c62f47cf810', null, '0', '2020-06-06 00:00:00', '2020-06-26 17:55:57', '0');
INSERT INTO `acl_user` VALUES ('4', 'tomadmin', '49d2369696bf3fb8f3c413879a335a1a', '汤姆哥', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '7c6100863f1c41f1a7cb7c62f47cf810', null, '0', '2020-06-06 00:00:00', '2020-07-12 22:05:18', '0');
INSERT INTO `acl_user` VALUES ('5', 'jerryadmin', '49d2369696bf3fb8f3c413879a335a1a', '杰瑞大佬', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '7c6100863f1c41f1a7cb7c62f47cf810', null, '0', '2020-06-06 00:00:00', '2020-06-26 17:56:59', '0');

-- ----------------------------
-- Table structure for acl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acl_user_role
-- ----------------------------
INSERT INTO `acl_user_role` VALUES ('1276875537796587521', '1', '1', '0', '2020-06-06 00:00:00', '2020-06-06 00:00:00');
INSERT INTO `acl_user_role` VALUES ('1282318776980733954', '1277140274224562177', '1276705571495489538', '0', '2020-07-12 22:19:55', '2020-07-12 22:19:55');
INSERT INTO `acl_user_role` VALUES ('1282320909746692097', '1277140458945904641', '1277795741468758017', '1', '2020-07-12 22:28:24', '2020-07-12 22:28:24');
INSERT INTO `acl_user_role` VALUES ('1282321035399651331', '1277140428734332929', '1282321035399651330', '1', '2020-07-12 22:28:54', '2020-07-12 22:28:54');
INSERT INTO `acl_user_role` VALUES ('1282321533250846722', '1277140428734332929', '1282321035399651330', '0', '2020-07-12 22:30:53', '2020-07-12 22:30:53');
INSERT INTO `acl_user_role` VALUES ('1282321715099090945', '1277140274224562177', '1277795741468758017', '1', '2020-07-12 22:31:36', '2020-07-12 22:31:36');
INSERT INTO `acl_user_role` VALUES ('1282321837866369025', '1277140274224562177', '1277795741468758017', '0', '2020-07-12 22:32:05', '2020-07-12 22:32:05');
INSERT INTO `acl_user_role` VALUES ('1282323358108631041', '1277140428734332929', '4', '0', '2020-07-12 22:38:08', '2020-07-12 22:38:08');
