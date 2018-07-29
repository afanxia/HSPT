/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : hspt

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2018-06-22 14:35:51
*/
drop database hspt;
create database hspt DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use hspt;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hspt_authorization
-- ----------------------------
DROP TABLE IF EXISTS `hspt_authorization`;
CREATE TABLE `hspt_authorization` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_authorization` bigint(18) NOT NULL COMMENT '授权主键',
  `access_key` varchar(20) DEFAULT NULL COMMENT 'access_key',
  `secret_key` varchar(20) DEFAULT NULL COMMENT 'secret_key',
  `auth_end_date` char(10) DEFAULT NULL COMMENT '授权截止日期',
  `pk_group` bigint(18) NOT NULL COMMENT '授权组织',
  PRIMARY KEY (`pk_authorization`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权管理，主要用户第三方调用系统服务使用';

-- ----------------------------
-- Records of hspt_authorization
-- ----------------------------
INSERT INTO `hspt_authorization` VALUES ('2018-03-30 17:31:00', '0', '2018-03-30 17:31:00', '377457784972640256', '2018-06-22 14:25:43', '377457784972640256', '429331724502040576', '5T83yoqbLQ6NjrgbBI0n', 'aq6BPCzBPMqwVWVyQi3I', '', '378181361514577920');

-- ----------------------------
-- Table structure for hspt_duties
-- ----------------------------
DROP TABLE IF EXISTS `hspt_duties`;
CREATE TABLE `hspt_duties` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_duties` bigint(18) NOT NULL COMMENT '职务主键',
  `duties_code` varchar(50) DEFAULT NULL COMMENT '职务编码',
  `duties_name` varchar(100) DEFAULT NULL COMMENT '职务名称',
  `duties_type` int(11) DEFAULT NULL COMMENT '类型 0 系统自建 1 组织自建',
  `pk_group` bigint(18) NOT NULL COMMENT '组织主键',
  PRIMARY KEY (`pk_duties`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职务信息表';

-- ----------------------------
-- Records of hspt_duties
-- ----------------------------

-- ----------------------------
-- Table structure for hspt_employees
-- ----------------------------
DROP TABLE IF EXISTS `hspt_employees`;
CREATE TABLE `hspt_employees` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_employees` bigint(18) NOT NULL COMMENT '人员主键',
  `pk_organization` bigint(18) DEFAULT NULL COMMENT '机构主键',
  `pk_group` bigint(18) DEFAULT NULL COMMENT '组织主键',
  `employees_code` varchar(50) DEFAULT NULL COMMENT '工号',
  `employees_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`pk_employees`),
  KEY `pk_group` (`pk_group`),
  KEY `pk_organization` (`pk_organization`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员信息表';

-- ----------------------------
-- Records of hspt_employees
-- ----------------------------

-- ----------------------------
-- Table structure for hspt_employees_duties
-- ----------------------------
DROP TABLE IF EXISTS `hspt_employees_duties`;
CREATE TABLE `hspt_employees_duties` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_employees_duties` bigint(18) NOT NULL COMMENT '主键',
  `pk_employees` bigint(18) NOT NULL COMMENT '人员主键',
  `pk_duties` bigint(18) NOT NULL COMMENT '职务主键',
  PRIMARY KEY (`pk_employees_duties`),
  KEY `pk_duties` (`pk_duties`),
  KEY `pk_employees` (`pk_employees`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员职务信息表';

-- ----------------------------
-- Records of hspt_employees_duties
-- ----------------------------

-- ----------------------------
-- Table structure for hspt_group
-- ----------------------------
DROP TABLE IF EXISTS `hspt_group`;
CREATE TABLE `hspt_group` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_group` bigint(18) NOT NULL COMMENT '组织主键',
  `group_code` varchar(32) DEFAULT NULL COMMENT '组织编码',
  `group_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `group_type` int(11) DEFAULT '2' COMMENT '组织类型（0为 自建、1为授权、2为其他等 默认为2）',
  PRIMARY KEY (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织信息表';

-- ----------------------------
-- Records of hspt_group
-- ----------------------------
INSERT INTO `hspt_group` VALUES ('2018-01-04 10:46:54', '0', '2018-01-04 10:47:03', '377457784972640256', '2018-06-21 14:40:25', null, '-1', 'sys', '平台', '0');
INSERT INTO `hspt_group` VALUES ('2018-02-08 14:02:56', '0', '2017-11-09 13:57:43', '377457784972640256', '2018-02-08 14:02:56', '393717664398180352', '378181361514577920', 'demo', '演示组织', '0');
INSERT INTO `hspt_group` VALUES ('2017-11-09 14:24:29', '0', '2017-11-09 14:24:29', '377457784972640256', '2018-06-21 14:41:30', null, '378188098061729792', 'pub', '公开组织', '2');

-- ----------------------------
-- Table structure for hspt_menu
-- ----------------------------
DROP TABLE IF EXISTS `hspt_menu`;
CREATE TABLE `hspt_menu` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_menu` bigint(18) NOT NULL COMMENT '菜单主键',
  `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_type` int(11) NOT NULL COMMENT '菜单类型 0为后台系统菜单 1为前台业务菜单',
  `pk_f_menu` bigint(18) DEFAULT NULL COMMENT '上级菜单主键',
  `order_code` varchar(30) DEFAULT NULL COMMENT '内部序号 自动生成用于排序使用',
  `menu_url` varchar(500) DEFAULT NULL COMMENT '访问URL',
  `is_end` char(1) DEFAULT 'N' COMMENT '是否末级 Y为末级 N或者空为非末级 默认为N',
  `menu_lev` int(11) DEFAULT NULL COMMENT '菜单级次',
  `menu_ico` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`pk_menu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单信息表';

-- ----------------------------
-- Records of hspt_menu
-- ----------------------------
INSERT INTO `hspt_menu` VALUES ('2018-04-03 20:40:04', '0', '2017-11-09 19:17:20', '377457784972640256', '2018-04-03 20:40:04', '402395440823140352', '378261796122787842', '010', '问卷管理', '0', '0', '010', '/survey', 'N', '1', 'survey');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000960', '010010', '问卷列表', '0', '378261796122787842', '010010', '/survey/list', 'Y', '2', 'list3');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000961', '010020', '问卷分类', '0', '378261796122787842', '010020', '/survey/type', 'Y', '2', 'type');

INSERT INTO `hspt_menu` VALUES ('2018-04-03 20:40:04', '0', '2017-11-09 19:17:20', '377457784972640256', '2018-04-03 20:40:04', '402395440823140352', '378261796122787843', '020', '随访管理', '0', '0', '020', '/plan', 'Y', '1', 'list');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000962', '020010', '随访计划', '0', '378261796122787843', '020010', '/plan/list', 'Y', '2', 'list1');

INSERT INTO `hspt_menu` VALUES ('2018-04-03 20:40:04', '0', '2017-11-09 19:17:20', '377457784972640256', '2018-04-03 20:40:04', '402395440823140352', '378261796122787844', '030', '病人管理', '0', '0', '030', '/patient', 'N', '1', 'patient');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000963', '030010', '病人列表', '0', '378261796122787844', '030010', '/patient/list', 'Y', '2', 'case');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000964', '030020', '病人类型', '0', '378261796122787844', '030020', '/patient/type', 'Y', '2', 'example');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000965', '030030', '随访信息', '0', '378261796122787844', '030030', '/patient/deliveryInfo', 'Y', '2', 'list2');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000966', '030040', '回收站', '0', '378261796122787844', '030040', '/patient/recycle', 'Y', '2', 'recycle2');

INSERT INTO `hspt_menu` VALUES ('2018-04-03 20:40:04', '0', '2017-11-09 19:17:20', '377457784972640256', '2018-04-03 20:40:04', '402395440823140352', '378261796122787845', '040', '医生管理', '0', '0', '040', '/doctor', 'N', '1', 'doctor1');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000967', '040010', '医生列表', '0', '378261796122787845', '040010', '/doctor/list', 'Y', '2', 'doctor');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000968', '040020', '医院管理', '0', '378261796122787845', '040020', '/doctor/hospital', 'Y', '2', 'hospital1');

INSERT INTO `hspt_menu` VALUES ('2018-04-03 20:40:04', '0', '2017-11-09 19:17:20', '377457784972640256', '2018-04-03 20:40:04', '402395440823140352', '378261796122787840', '050', '系统管理', '0', '0', '050', '/sys', 'N', '1', 'setting1');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:20:33', '0', '2017-11-09 19:20:33', '377457784972640256', '2017-11-09 19:21:22', '377457784972640256', '378262605225000969', '050020', '菜单管理', '0', '378261796122787840', '050020', '/sys/menu', 'Y', '2', 'list');
INSERT INTO `hspt_menu` VALUES ('2017-12-21 20:41:39', '0', '2017-12-21 20:42:25', '377457784972640256', '2017-12-21 20:44:19', '377457784972640256', '378263121581572097', '050030', '角色管理', '0', '378261796122787840', '050030', '/sys/role', 'N', '2', 'role');
INSERT INTO `hspt_menu` VALUES ('2018-04-02 14:41:21', '0', '2017-11-09 19:22:58', '377457784972640256', '2018-04-02 14:41:21', '393717664398180352', '378263215357820928', '050040', '用户管理', '0', '378261796122787840', '050040', '/sys/user', 'Y', '2', 'user2');
INSERT INTO `hspt_menu` VALUES ('2018-04-02 14:41:27', '0', '2017-11-09 19:23:35', '377457784972640256', '2018-04-02 14:41:27', '393717664398180352', '378263367338426368', '050050', '组织管理', '0', '378261796122787840', '050050', '/sys/group', 'Y', '2', 'tree');
INSERT INTO `hspt_menu` VALUES ('2017-11-09 19:23:56', '0', '2017-11-09 19:23:56', '377457784972640256', '2017-11-09 19:24:31', '377457784972640256', '378263458849751040', '050999', '系统信息', '0', '378261796122787840', '050999', '/sys/info', 'N', '2', 'info1');
INSERT INTO `hspt_menu` VALUES ('2018-04-02 14:41:53', '0', '2017-11-09 19:25:07', '377457784972640256', '2018-04-02 14:41:53', '393717664398180352', '378263753197617152', '050999010', '系统日志', '0', '378263458849751040', '050999010', '/sys/info/log', 'Y', '3', 'log1');
INSERT INTO `hspt_menu` VALUES ('2018-04-02 14:41:59', '0', '2017-11-09 19:25:27', '377457784972640256', '2018-04-02 14:41:59', '393717664398180352', '378263837368909824', '050999020', '异常日志', '0', '378263458849751040', '050999020', '/sys/info/error', 'Y', '3', 'error');

-- ----------------------------
-- Table structure for hspt_menu_permissions
-- ----------------------------
DROP TABLE IF EXISTS `hspt_menu_permissions`;
CREATE TABLE `hspt_menu_permissions` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_menu_permissions` bigint(18) NOT NULL COMMENT '菜单权限主键',
  `pk_permissions` bigint(18) NOT NULL COMMENT '权限主键',
  `pk_menu` bigint(18) NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`pk_menu_permissions`),
  KEY `pk_menu` (`pk_menu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- ----------------------------
-- Records of hspt_menu_permissions
-- ----------------------------
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934461', '459724809119465411', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934462', '459724809119465412', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934463', '459724809119465413', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934464', '459724809119465414', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934465', '459724809119465411', '378262605225000960');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934466', '459724809119465412', '378262605225000960');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934467', '459724809119465413', '378262605225000960');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934468', '459724809119465414', '378262605225000960');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095231', '459724809119465421', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095232', '459724809119465422', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095233', '459724809119465423', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095234', '459724809119465424', '378261796122787842');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095235', '459724809119465421', '378262605225000961');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095236', '459724809119465422', '378262605225000961');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095237', '459724809119465423', '378262605225000961');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095238', '459724809119465424', '378262605225000961');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730041', '459724809119465431', '378261796122787843');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730042', '459724809119465432', '378261796122787843');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730043', '459724809119465433', '378261796122787843');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730044', '459724809119465434', '378261796122787843');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730045', '459724809119465431', '378262605225000962');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730046', '459724809119465432', '378262605225000962');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730047', '459724809119465433', '378262605225000962');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730048', '459724809119465434', '378262605225000962');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616121', '459724809119465441', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616122', '459724809119465442', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616123', '459724809119465443', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616124', '459724809119465444', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616125', '459724809119465441', '378262605225000963');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616126', '459724809119465442', '378262605225000963');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616127', '459724809119465443', '378262605225000963');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616128', '459724809119465444', '378262605225000963');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502201', '459724809119465451', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502202', '459724809119465452', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502203', '459724809119465453', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502204', '459724809119465454', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502205', '459724809119465451', '378262605225000964');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502206', '459724809119465452', '378262605225000964');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502207', '459724809119465453', '378262605225000964');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502208', '459724809119465454', '378262605225000964');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611071', '459724809119465461', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611072', '459724809119465462', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611073', '459724809119465463', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611074', '459724809119465464', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611075', '459724809119465461', '378262605225000965');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611076', '459724809119465462', '378262605225000965');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611077', '459724809119465463', '378262605225000965');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611078', '459724809119465464', '378262605225000965');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719931', '459724809119465473', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719932', '459724809119465474', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719933', '459724809119465475', '378261796122787844');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719934', '459724809119465473', '378262605225000966');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719935', '459724809119465474', '378262605225000966');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719936', '459724809119465475', '378262605225000966');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411711', '459724809119465481', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411712', '459724809119465482', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411713', '459724809119465483', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411714', '459724809119465484', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411715', '459724809119465481', '378262605225000967');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411716', '459724809119465482', '378262605225000967');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411717', '459724809119465483', '378262605225000967');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411718', '459724809119465484', '378262605225000967');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103481', '459724809119465491', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103482', '459724809119465492', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103483', '459724809119465493', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103484', '459724809119465494', '378261796122787845');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103485', '459724809119465491', '378262605225000968');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103486', '459724809119465492', '378262605225000968');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103487', '459724809119465493', '378262605225000968');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103488', '459724809119465494', '378262605225000968');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795261', '459724809119465501', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795262', '459724809119465502', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795263', '459724809119465503', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795264', '459724809119465504', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795265', '459724809119465501', '378262605225000969');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795266', '459724809119465502', '378262605225000969');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795267', '459724809119465503', '378262605225000969');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795268', '459724809119465504', '378262605225000969');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681341', '459724809119465511', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681342', '459724809119465512', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681343', '459724809119465513', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681344', '459724809119465514', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681345', '459724809119465511', '378263121581572097');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681346', '459724809119465512', '378263121581572097');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681347', '459724809119465513', '378263121581572097');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681348', '459724809119465514', '378263121581572097');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984511', '459724809119465521', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984512', '459724809119465522', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984513', '459724809119465523', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984514', '459724809119465524', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984515', '459724809119465521', '378263215357820928');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984516', '459724809119465522', '378263215357820928');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984517', '459724809119465523', '378263215357820928');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984518', '459724809119465524', '378263215357820928');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481981', '459724809119465531', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481982', '459724809119465532', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481983', '459724809119465533', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481984', '459724809119465534', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481985', '459724809119465531', '378263367338426368');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481986', '459724809119465532', '378263367338426368');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481987', '459724809119465533', '378263367338426368');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481988', '459724809119465534', '378263367338426368');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562361', '459724809119465541', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562362', '459724809119465542', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562363', '459724809119465541', '378263458849751040');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562364', '459724809119465542', '378263458849751040');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562365', '459724809119465541', '378263753197617152');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562366', '459724809119465542', '378263753197617152');

INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476921', '459724809119465551', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476922', '459724809119465552', '378261796122787840');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476923', '459724809119465551', '378263458849751040');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476924', '459724809119465552', '378263458849751040');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476925', '459724809119465551', '378263837368909824');
INSERT INTO `hspt_menu_permissions` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476926', '459724809119465552', '378263837368909824');

-- ----------------------------
-- Table structure for hspt_module
-- ----------------------------
DROP TABLE IF EXISTS `hspt_module`;
CREATE TABLE `hspt_module` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_module` bigint(18) NOT NULL COMMENT '模块主键',
  `module_id` varchar(500) DEFAULT NULL COMMENT '模块ID',
  `module_name` varchar(200) DEFAULT NULL COMMENT '模块名称',
  PRIMARY KEY (`pk_module`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模块信息';

-- ----------------------------
-- Records of hspt_module
-- ----------------------------
INSERT INTO `hspt_module` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462162444288', 'gateway', '网关中心');
INSERT INTO `hspt_module` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724807987003392', 'user', '用户中心');

-- ----------------------------
-- Table structure for hspt_org
-- ----------------------------
DROP TABLE IF EXISTS `hspt_org`;
CREATE TABLE `hspt_org` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_org` bigint(18) NOT NULL COMMENT '机构主键',
  `org_code` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '编码',
  `org_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `nodetype` int(11) DEFAULT NULL COMMENT '节点类型 0:集团 1:公司 2:部门',
  `pk_f_org` bigint(18) DEFAULT NULL COMMENT '上级主键',
  `pk_group` bigint(18) NOT NULL COMMENT '组织主键',
  `org_lev` int(11) DEFAULT NULL COMMENT '机构级次',
  `is_end` char(1) DEFAULT 'N' COMMENT '是否末级 Y为末级 N或者空为非末级 默认为N',
  `order_code` varchar(30) DEFAULT NULL COMMENT '内部序号 自动生成用于排序使用',
  PRIMARY KEY (`pk_org`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构信息表';

-- ----------------------------
-- Records of hspt_org
-- ----------------------------
INSERT INTO `hspt_org` VALUES ('2018-03-30 17:23:53', '0', '2017-11-30 17:45:52', '378182074307182592', '2018-03-30 17:23:53', '378187570242125824', '378181361514577920', 'demo', '演示组织', '0', '-1', '378181361514577920', '0', 'Y', '0');
INSERT INTO `hspt_org` VALUES ('2017-12-28 20:29:12', '0', '2018-06-21 14:46:11', '378182074307182592', '2018-06-21 14:46:18', '378187570242125824', '378188098061729792', 'pub', '公开组织', '0', '-1', '378188098061729792', '0', 'Y', '0');

-- ----------------------------
-- Table structure for hspt_permissions
-- ----------------------------
DROP TABLE IF EXISTS `hspt_permissions`;
CREATE TABLE `hspt_permissions` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_permissions` bigint(18) NOT NULL COMMENT '权限主键',
  `permissions_code` varchar(100) DEFAULT NULL COMMENT '权限编码',
  `permissions_name` varchar(200) DEFAULT NULL COMMENT '权限名称',
  `permissions_info` varchar(150) DEFAULT NULL COMMENT '描述',
  `pk_group` bigint(18) DEFAULT NULL COMMENT '组织主键',
  PRIMARY KEY (`pk_permissions`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息表';

-- ----------------------------
-- Records of hspt_permissions
-- ----------------------------
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465411', 'survey:add', '添加', '问卷管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465412', 'survey:list', '查看', '问卷管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465413', 'survey:update', '修改', '问卷管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465414', 'survey:delete', '删除', '问卷管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465421', 'surveyType:add', '添加', '问卷类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465422', 'surveyType:list', '查看', '问卷类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465423', 'surveyType:update', '修改', '问卷类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465424', 'surveyType:delete', '删除', '问卷类型管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465431', 'plan:add', '添加', '随访计划管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465432', 'plan:list', '查看', '随访计划管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465433', 'plan:update', '修改', '随访计划管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465434', 'plan:delete', '删除', '随访计划管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465441', 'patient:add', '添加', '病人管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465442', 'patient:list', '查看', '病人管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465443', 'patient:update', '修改', '病人管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465444', 'patient:delete', '删除', '病人管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465451', 'patientType:add', '添加', '病人类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465452', 'patientType:list', '查看', '病人类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465453', 'patientType:update', '修改', '病人类型管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465454', 'patientType:delete', '删除', '病人类型管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465461', 'delivery:add', '添加', '随访信息管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465462', 'delivery:list', '查看', '随访信息管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465463', 'delivery:update', '修改', '随访信息管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465464', 'delivery:delete', '删除', '随访信息管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465473', 'recycle:list', '查看', '回收站管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465474', 'recycle:update', '修改', '回收站管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465475', 'recycle:delete', '删除', '回收站管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465481', 'doctor:add', '添加', '医生管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465482', 'doctor:list', '查看', '医生管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465483', 'doctor:update', '修改', '医生管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465484', 'doctor:delete', '删除', '医生管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465491', 'hospital:add', '添加', '医院管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465492', 'hospital:list', '查看', '医院管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465493', 'hospital:update', '修改', '医院管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465494', 'hospital:delete', '删除', '医院管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465501', 'menu:add', '添加', '菜单管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465502', 'menu:list', '查看', '菜单管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465503', 'menu:update', '修改', '菜单管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465504', 'menu:delete', '删除', '菜单管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465511', 'role:add', '添加', '角色管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465512', 'role:list', '查看', '角色管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465513', 'role:update', '修改', '角色管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465514', 'role:delete', '删除', '角色管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465521', 'user:add', '添加', '用户管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465522', 'user:list', '查看', '用户管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465523', 'user:update', '修改', '用户管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465524', 'user:delete', '删除', '用户管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465531', 'group:add', '添加', '组织管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465532', 'group:list', '查看', '组织管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465533', 'group:update', '修改', '组织管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465534', 'group:delete', '删除', '组织管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465541', 'log:list', '查看', '系统日志管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465542', 'log:delete', '删除', '系统日志管理', '-1');

INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465551', 'error:list', '查看', '系统异常管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465552', 'error:delete', '删除', '系统异常管理', '-1');
-- 下面这些是原有的设计，与resource配合使用，现已封存不用，改为上面直观的permission控制
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:42', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809119465472', 'user_sys_info_error', '用户中心系统异常日志管理接口', '提供对异常日志进行查询功能', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724809580838912', 'user_sys_group', '用户中心系统组织管理接口', '提供对系统组织信息的维护管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724809958326272', 'user_sys', '用户中心首页管理公共接口', '后台首页管理，提供一些图表类的接口', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810121904128', 'user_sys_info_log', '用户中心系统日志管理接口', '提供对业务执行日志进行查询功能', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810348396544', 'user_sys_menu_auth', '用户中心系统菜单授权接口', '提供对系统菜单授权的维护管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810537140224', 'user_sys_menu_conf', '用户中心系统菜单配置接口', '提供对系统菜单信息的维护管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811128537088', 'user_sys_permissions', '用户中心功能权限管理接口', '提供对功能权限信息的维护管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811388583936', 'user_sys_role_auth', '用户中心系统角色授权接口', '提供对系统角色授权的管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811497635840', 'user_sys_role_conf', '用户中心系统角色配置接口', '提供对系统角色信息的维护管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811921260544', 'user_sys_role_user', '用户中心系统角色用户授权接口', '提供对系统角色用户授权的管理', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811988369408', 'user_sys_user', '用户中心系统用户管理接口', '提供系统用户的管理功能', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812311330816', 'user_userEmployees', '用户中心用户员工关系接口', '用户员工关系接口，提供信息的获取与维护', '-1');
INSERT INTO `hspt_permissions` VALUES ('2018-06-22 14:31:43', '2', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812617515008', 'user_users', '用户中心用户基础服务', '提供用户的相关接口管理', '-1');

-- ----------------------------
-- Table structure for hspt_permissions_resource
-- ----------------------------
DROP TABLE IF EXISTS `hspt_permissions_resource`;
CREATE TABLE `hspt_permissions_resource` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_permissions_resource` bigint(18) NOT NULL COMMENT '权限资源主键',
  `pk_permissions` bigint(18) NOT NULL COMMENT '权限主键',
  `pk_resource` bigint(18) NOT NULL COMMENT '资源主键',
  PRIMARY KEY (`pk_permissions_resource`),
  KEY `pk_permissions` (`pk_permissions`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限资源列表';

-- ----------------------------
-- Records of hspt_permissions_resource
-- ----------------------------
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934464', '459724809119465472', '459724809064939520');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095232', '459724809119465472', '459724809324986368');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730048', '459724809119465472', '459724809480175616');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616128', '459724809580838912', '459724809555673088');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502208', '459724809580838912', '459724809639559168');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611072', '459724809580838912', '459724809715056640');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719936', '459724809580838912', '459724809782165504');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411712', '459724809580838912', '459724809857662976');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103488', '459724809958326272', '459724809937354752');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795264', '459724809958326272', '459724810017046528');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681344', '459724810121904128', '459724810096738304');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984512', '459724810121904128', '459724810184818688');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481984', '459724810121904128', '459724810251927552');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562368', '459724810348396544', '459724810331619328');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476928', '459724810348396544', '459724810411311104');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810491002880', '459724810348396544', '459724810465837056');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810549723136', '459724810537140224', '459724810524557312');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810608443392', '459724810537140224', '459724810583277568');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810738466816', '459724810537140224', '459724810679746560');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810809769984', '459724810537140224', '459724810780409856');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810872684544', '459724810537140224', '459724810847518720');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810943987712', '459724810537140224', '459724810910433280');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811006902272', '459724810537140224', '459724810973347840');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811069816832', '459724810537140224', '459724811044651008');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811145314304', '459724811128537088', '459724811111759872');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811216617472', '459724811128537088', '459724811183063040');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811275337728', '459724811128537088', '459724811250171904');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811346640896', '459724811128537088', '459724811317280768');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811405361152', '459724811388583936', '459724811371806720');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811522801664', '459724811497635840', '459724811476664320');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811627659264', '459724811497635840', '459724811594104832');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811694768128', '459724811497635840', '459724811661213696');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811753488384', '459724811497635840', '459724811719933952');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811816402944', '459724811497635840', '459724811795431424');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811879317504', '459724811497635840', '459724811854151680');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724811942232064', '459724811921260544', '459724811912871936');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724812005146624', '459724811988369408', '459724811975786496');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724812063866880', '459724811988369408', '459724812038701056');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724812114198528', '459724811988369408', '459724812089032704');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724812160335872', '459724811988369408', '459724812143558656');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812210667520', '459724811988369408', '459724812189696000');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812269387776', '459724811988369408', '459724812244221952');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812328108032', '459724812311330816', '459724812294553600');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812378439680', '459724812311330816', '459724812353273856');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812428771328', '459724812311330816', '459724812407799808');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812495880192', '459724812311330816', '459724812470714368');
INSERT INTO `hspt_permissions_resource` VALUES ('2018-06-22 14:22:17', '0', '2018-06-22 14:22:17', null, null, null, '459724812634292224', '459724812617515008', '459724812596543488');

-- ----------------------------
-- Table structure for hspt_resource
-- ----------------------------
DROP TABLE IF EXISTS `hspt_resource`;
CREATE TABLE `hspt_resource` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_resource` bigint(18) NOT NULL COMMENT '资源主键',
  `module_name` varchar(200) DEFAULT NULL COMMENT '模块名称',
  `service_name` varchar(50) DEFAULT NULL COMMENT '服务名称',
  `module_id` varchar(500) DEFAULT NULL COMMENT '模块ID',
  `api_name` varchar(500) DEFAULT NULL COMMENT '接口名称',
  `method_name` varchar(500) DEFAULT NULL COMMENT '方法名称',
  `module_path` varchar(200) DEFAULT NULL COMMENT '模块路径',
  `method_path` varchar(1000) DEFAULT NULL COMMENT '请求路径',
  `url` varchar(1000) DEFAULT NULL COMMENT '地址',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`pk_resource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统资源表';

-- ----------------------------
-- Records of hspt_resource
-- ----------------------------
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462560903168', '网关中心', '网关中心公共服务接口', 'gateway', '用户注册', 'register', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/register', '用户注册，返回TOKEN信息。为了数据安全，此接口不允许使用非加密方式调用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462820950016', '网关中心', '网关中心公共服务接口', 'gateway', '获取TOKEN的详细信息', 'tokenInfo', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/token', '第三方系统授权登陆后，页面集成用到的token关联用户信息');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462862893056', '网关中心', '网关中心公共服务接口', 'gateway', '刷新TOKEN', 'refreshToken', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/refresh', '刷新TOKEN,一般用于TOKEN锁定或者过期的时候使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462909030400', '网关中心', '网关中心公共服务接口', 'gateway', '用户登陆', 'login', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/login', '用户登陆，返回TOKEN信息。为了数据安全，此接口不允许使用非加密方式调用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462950973440', '网关中心', '网关中心公共服务接口', 'gateway', '授权登陆', 'sso', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/sso', '第三方系统授权登陆，返回TOKEN信息。为了数据安全，此接口不允许使用非加密方式调用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724462984527872', '网关中心', '网关中心公共服务接口', 'gateway', '获取服务器时间信息', 'getSysDate', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/sysDate', '获取服务器的时间信息');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724463018082304', '网关中心', '网关中心公共服务接口', 'gateway', '解锁TOKEN', 'unlockedToken', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/unlocked', '解锁TOKEN,需要指定角色才有此权限');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724463080996864', '网关中心', '网关中心公共服务接口', 'gateway', '锁定TOKEN', 'lockedToken', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/locked', '锁定TOKEN,需要指定角色才有此权限');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724463118745600', '网关中心', '网关中心公共服务接口', 'gateway', '用户退出', 'logout', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/logout', '用户退出,一般用于网站登陆时候使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724463160688640', '网关中心', '网关中心公共服务接口', 'gateway', '获取RSA公钥', 'getPubKey', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/key', '获取RSA公钥，用于客户端加密AES密钥使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:51', '0', '2018-06-22 14:20:53', null, '2018-06-22 14:31:51', null, '459724463202631680', '网关中心', '网关中心公共服务接口', 'gateway', '格式化日期用于手机应用使用', 'formatShowDate', '/PubApi', 'org.hspt.controller.BaseController', '/PubApi/formatShowDate/{date}/{time}', '格式化日期用于手机应用使用，显示为几分钟前、几小时前等');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724808645509120', '用户中心', '用户中心公有接口', 'user', '获取组织树', 'getGroupTree', '/base', 'org.hspt.controller.BaseController', '/base/group/tree', '获取组织树,主要用于构造菜单使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809027190784', '用户中心', '用户中心公有接口', 'user', '获取全部组织信息', 'getGroupAll', '/base', 'org.hspt.controller.BaseController', '/base/group/all', '获取全部组织信息,主要用于下拉使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809064939520', '用户中心', '用户中心系统异常日志管理接口', 'user', '获取日志记录数', 'getErrorLogCount', '/sys/info/error', 'org.hspt.controller.sys.SysErrLogController', '/sys/info/error/count', '获取日志记录数，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809324986368', '用户中心', '用户中心系统异常日志管理接口', 'user', '获取日志详细信息', 'getErrorLogItem', '/sys/info/error', 'org.hspt.controller.sys.SysErrLogController', '/sys/info/error/{id}', '获取日志详细信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809480175616', '用户中心', '用户中心系统异常日志管理接口', 'user', '获取日志列表信息', 'getErrorLogList', '/sys/info/error', 'org.hspt.controller.sys.SysErrLogController', '/sys/info/error/', '获取日志列表信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809555673088', '用户中心', '用户中心系统组织管理接口', 'user', '修改组织信息', 'setGroup', '/sys/group', 'org.hspt.controller.sys.SysGroupController', '/sys/group/{groupid}', '修改组织信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809639559168', '用户中心', '用户中心系统组织管理接口', 'user', '获取组织信息', 'getGroupList', '/sys/group', 'org.hspt.controller.sys.SysGroupController', '/sys/group/', '获取组织基本信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809715056640', '用户中心', '用户中心系统组织管理接口', 'user', '添加组织', 'addGroup', '/sys/group', 'org.hspt.controller.sys.SysGroupController', '/sys/group/', '添加组织，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:42', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:42', null, '459724809782165504', '用户中心', '用户中心系统组织管理接口', 'user', '获取组织记录总数', 'getGroupCount', '/sys/group', 'org.hspt.controller.sys.SysGroupController', '/sys/group/count', '获取组织记录总数，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724809857662976', '用户中心', '用户中心系统组织管理接口', 'user', '删除组织', 'delGroup', '/sys/group', 'org.hspt.controller.sys.SysGroupController', '/sys/group/{groupid}', '删除组织，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724809937354752', '用户中心', '用户中心首页管理公共接口', 'user', '获取今天错误日志分组信息', 'getTodayErrorCount', '/sys', 'org.hspt.controller.sys.SysIndexController', '/sys/todayErrorCount', '获取今天错误日志分组信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810017046528', '用户中心', '用户中心首页管理公共接口', 'user', '获取过去7天各模块请求信息', 'getRequestCountByWeek', '/sys', 'org.hspt.controller.sys.SysIndexController', '/sys/requestCountByWeek', '获取过去7天各模块请求信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810096738304', '用户中心', '用户中心系统日志管理接口', 'user', '获取日志详细信息', 'getLogItem', '/sys/info/log', 'org.hspt.controller.sys.SysLogController', '/sys/info/log/{id}', '获取日志详细信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810184818688', '用户中心', '用户中心系统日志管理接口', 'user', '获取日志列表信息', 'getLogList', '/sys/info/log', 'org.hspt.controller.sys.SysLogController', '/sys/info/log/', '获取日志列表信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810251927552', '用户中心', '用户中心系统日志管理接口', 'user', '获取日志记录数', 'getLogCount', '/sys/info/log', 'org.hspt.controller.sys.SysLogController', '/sys/info/log/count', '获取日志记录数，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810331619328', '用户中心', '用户中心系统菜单授权接口', 'user', '菜单授权', 'addMenuAuth', '/sys/menu/auth', 'org.hspt.controller.sys.SysMenuAuthController', '/sys/menu/auth/', '菜单授权，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810411311104', '用户中心', '用户中心系统菜单授权接口', 'user', '取消菜单授权', 'delMenuAuth', '/sys/menu/auth', 'org.hspt.controller.sys.SysMenuAuthController', '/sys/menu/auth/{menuPermissionsId}', '取消菜单授权，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810465837056', '用户中心', '用户中心系统菜单授权接口', 'user', '获取菜单授权列表', 'getMenuAuthList', '/sys/menu/auth', 'org.hspt.controller.sys.SysMenuAuthController', '/sys/menu/auth/{menuId}', '获取菜单授权接口列表，主要用于取消授权使用');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810524557312', '用户中心', '用户中心系统菜单配置接口', 'user', '获取菜单总数', 'getCount', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/count', '获取菜单总数，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810583277568', '用户中心', '用户中心系统菜单配置接口', 'user', '获取全部菜单', 'getItems', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/', '获取全部菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810679746560', '用户中心', '用户中心系统菜单配置接口', 'user', '获取角色菜单', 'getItemsByRole', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/getItemsByRole/{role_id}', '获取角色菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810780409856', '用户中心', '用户中心系统菜单配置接口', 'user', '添加菜单', 'addMenu', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/', '添加菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810847518720', '用户中心', '用户中心系统菜单配置接口', 'user', '获取菜单信息', 'getMenu', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/{menuId}', '获取菜单信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810910433280', '用户中心', '用户中心系统菜单配置接口', 'user', '获取角色未授权菜单', 'getRoleNoMenu', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/getRoleNoMenu/{role_id}', '获取角色未授权菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724810973347840', '用户中心', '用户中心系统菜单配置接口', 'user', '删除菜单', 'delMenu', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/{menuId}', '删除菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811044651008', '用户中心', '用户中心系统菜单配置接口', 'user', '修改菜单', 'setMenu', '/sys/menu/conf', 'org.hspt.controller.sys.SysMenuController', '/sys/menu/conf/{menuId}', '修改菜单，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811111759872', '用户中心', '用户中心功能权限管理接口', 'user', '获取权限列表', 'getPermissions', '/sys/permissions', 'org.hspt.controller.sys.SysPermissionsController', '/sys/permissions/', '获取权限列表，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811183063040', '用户中心', '用户中心功能权限管理接口', 'user', '获取权限信息', 'getPermission', '/sys/permissions', 'org.hspt.controller.sys.SysPermissionsController', '/sys/permissions/{permissionId}', '获取权限信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811250171904', '用户中心', '用户中心功能权限管理接口', 'user', '获取全部信息', 'getAll', '/sys/permissions', 'org.hspt.controller.sys.SysPermissionsController', '/sys/permissions/all', '获取全部信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811317280768', '用户中心', '用户中心功能权限管理接口', 'user', '删除权限信息', 'delPermission', '/sys/permissions', 'org.hspt.controller.sys.SysPermissionsController', '/sys/permissions/{permissionId}', '删除权限，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811371806720', '用户中心', '用户中心系统角色授权接口', 'user', '角色菜单授权', 'addRoleMenu', '/sys/role/auth', 'org.hspt.controller.sys.SysRoleAuthController', '/sys/role/auth/', '角色菜单授权，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811476664320', '用户中心', '用户中心系统角色配置接口', 'user', '获取记录总数', 'getCount', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/count', '获取全部角色，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811594104832', '用户中心', '用户中心系统角色配置接口', 'user', '获取全部角色', 'getAll', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/all', '获取全部角色多用于下拉，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811661213696', '用户中心', '用户中心系统角色配置接口', 'user', '获取全部角色，分页查询', 'getItems', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/', '获取全部角色，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811719933952', '用户中心', '用户中心系统角色配置接口', 'user', '添加系统角色', 'addSysRole', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/', '添加系统角色，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811795431424', '用户中心', '用户中心系统角色配置接口', 'user', '修改系统角色', 'setSysRole', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/{roleId}', '修改系统角色，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811854151680', '用户中心', '用户中心系统角色配置接口', 'user', '删除系统角色', 'delSysRole', '/sys/role/conf', 'org.hspt.controller.sys.SysRoleController', '/sys/role/conf/{roleId}', '删除角色，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811912871936', '用户中心', '用户中心系统角色用户授权接口', 'user', '角色用户授权', 'addRoleUser', '/sys/role/user', 'org.hspt.controller.sys.SysRoleUserController', '/sys/role/user/', '角色用户授权，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724811975786496', '用户中心', '用户中心系统用户管理接口', 'user', '获取用户信息', 'getCount', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/count', '获取系统用户信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724812038701056', '用户中心', '用户中心系统用户管理接口', 'user', '注册管理用户', 'addAdmin', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/', '注册管理用户，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724812089032704', '用户中心', '用户中心系统用户管理接口', 'user', '注册组织管理员', 'addGroupAdmin', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/group/{group_id}', '注册组织管理员，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:16', null, '2018-06-22 14:31:43', null, '459724812143558656', '用户中心', '用户中心系统用户管理接口', 'user', '创建指定角色用户', 'addUserByRole', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/role/{role_id}', '创建指定角色用户，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812189696000', '用户中心', '用户中心系统用户管理接口', 'user', '获取用户信息', 'getUser', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/', '获取系统用户信息，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812244221952', '用户中心', '用户中心系统用户管理接口', 'user', '锁定用户', 'delUser', '/sys/user', 'org.hspt.controller.sys.SysUserController', '/sys/user/{userId}', '锁定用户，系统管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812294553600', '用户中心', '用户中心用户员工关系接口', 'user', '获取组织用户列表信息', 'getUserList', '/userEmployees', 'org.hspt.controller.sys.SysUserEmployeesRelaController', '/userEmployees/userList/{group_id}', '获取组织用户列表信息，组织管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812353273856', '用户中心', '用户中心用户员工关系接口', 'user', '用户解绑员工', 'userDebindEmp', '/userEmployees', 'org.hspt.controller.sys.SysUserEmployeesRelaController', '/userEmployees/{employees_id}', '用户解绑员工，组织管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812407799808', '用户中心', '用户中心用户员工关系接口', 'user', '用户绑定员工', 'userBindEmp', '/userEmployees', 'org.hspt.controller.sys.SysUserEmployeesRelaController', '/userEmployees/', '用户绑定员工，组织管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812470714368', '用户中心', '用户中心用户员工关系接口', 'user', '获取用户员工信息', 'getUserEmpInfo', '/userEmployees', 'org.hspt.controller.sys.SysUserEmployeesRelaController', '/userEmployees/{employees_id}', '获取用户员工信息，组织管理员默认可以访问');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812529434624', '用户中心', '用户中心用户基础服务', 'user', '修改密码', 'password', '/users', 'org.hspt.controller.UserController', '/users/password', '修改密码');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812554600448', '用户中心', '用户中心用户基础服务', 'user', '注册普通用户', 'addUser', '/users', 'org.hspt.controller.UserController', '/users/register', '注册普通用户');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812571377664', '用户中心', '用户中心用户基础服务', 'user', '授权登陆', 'sso', '/users', 'org.hspt.controller.UserController', '/users/sso', '第三方系统授权登陆，返回TOKEN信息');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812596543488', '用户中心', '用户中心用户基础服务', 'user', '变更用户基础信息', 'setUserBase', '/users', 'org.hspt.controller.UserController', '/users/{pkUser}/base', '变更用户姓名、状态、失效日期，其他信息均不提供变更');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812659458048', '用户中心', '用户中心用户基础服务', 'user', '登陆', 'login', '/users', 'org.hspt.controller.UserController', '/users/login', '用户登陆');
INSERT INTO `hspt_resource` VALUES ('2018-06-22 14:31:43', '0', '2018-06-22 14:22:17', null, '2018-06-22 14:31:43', null, '459724812684623872', '用户中心', '用户中心用户基础服务', 'user', '获取用户菜单', 'getUserMenu', '/users', 'org.hspt.controller.UserController', '/users/menu', '获取用户菜单，如果出现菜单授权变更，需要重新登陆');

-- ----------------------------
-- Table structure for hspt_role
-- ----------------------------
DROP TABLE IF EXISTS `hspt_role`;
CREATE TABLE `hspt_role` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_role` bigint(18) NOT NULL COMMENT '角色主键',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_info` longtext COMMENT '角色说明',
  `role_type` int(11) DEFAULT '1' COMMENT '角色类别 0为系统预置 1为用户创建',
  `pk_group` bigint(18) DEFAULT NULL COMMENT '创建组织',
  PRIMARY KEY (`pk_role`),
  KEY `role_code` (`role_code`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of hspt_role
-- ----------------------------
INSERT INTO `hspt_role` VALUES ('2018-05-31 17:15:57', '0', '2017-11-07 09:19:27', '377457784972640256', '2018-05-31 17:15:57', '377457784972640256', '377385783062953984', 'SYS_ADMIN', '系统管理员', '系统管理员', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2018-02-08 11:45:00', '0', '2017-11-07 09:21:35', '377457784972640256', '2018-02-08 11:45:00', '377457784972640256', '377385783532716032', 'GROUP_ADMIN', '组织管理员', '组织管理员', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2017-11-07 09:27:29', '0', '2017-11-07 09:27:03', '377457784972640256', '2017-11-09 14:17:51', '377457784972640256', '377386234021937152', 'PUB_USER', '开发注册用户', '开发注册用户', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2017-11-09 14:20:09', '0', '2017-11-09 14:20:09', '377457784972640256', '2018-06-21 14:47:08', '377457784972640256', '378187007148425216', 'SYS_DEV', '开发人员', '开发人员', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2017-12-13 11:27:23', '0', '2017-12-13 11:27:23', '378185327187066880', '2018-06-21 14:47:41', '377457784972640256', '390464715102355456', 'SYS_OPER', '运维人员', '系统运维人员', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2018-01-15 09:35:29', '0', '2018-01-15 09:35:29', '377457784972640256', '2018-06-21 14:47:43', '377457784972640256', '402395355716517888', 'SYS_BASE', '档案管理角色', '管理档案信息', '0', '-1');
INSERT INTO `hspt_role` VALUES ('2018-01-31 15:02:58', '0', '2018-01-31 15:02:58', '377457784972640256', '2018-06-21 14:47:46', '377457784972640256', '408275975067926528', 'SYS_CONF', '系统配置管理员', '维护系统管理功能', '0', '-1');

-- ----------------------------
-- Table structure for hspt_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `hspt_role_menu`;
CREATE TABLE `hspt_role_menu` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_role_menu` bigint(18) NOT NULL COMMENT '角色菜单主键',
  `pk_menu` bigint(18) NOT NULL COMMENT '菜单主键',
  `pk_role` bigint(18) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`pk_role_menu`),
  KEY `pk_role` (`pk_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单信息表';

-- ----------------------------
-- Records of hspt_role_menu
-- ----------------------------
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934461', '378261796122787842', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934462', '378261796122787842', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934467', '378262605225000960', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934468', '378262605225000960', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095237', '378262605225000961', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095238', '378262605225000961', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730041', '378261796122787843', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730042', '378261796122787843', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730043', '378262605225000962', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730044', '378262605225000962', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616121', '378261796122787844', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616122', '378261796122787844', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616127', '378262605225000963', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616128', '378262605225000963', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502207', '378262605225000964', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809681502208', '378262605225000964', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611077', '378262605225000965', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611078', '378262605225000965', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719935', '378262605225000966', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809815719936', '378262605225000966', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411711', '378261796122787845', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411712', '378261796122787845', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411717', '378262605225000967', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411718', '378262605225000967', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103487', '378262605225000968', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809975103488', '378262605225000968', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795261', '378261796122787840', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795262', '378261796122787840', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795267', '378262605225000969', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795268', '378262605225000969', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681347', '378263121581572097', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681348', '378263121581572097', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984517', '378263215357820928', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810209984518', '378263215357820928', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481987', '378263367338426368', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810285481988', '378263367338426368', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562363', '378263458849751040', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562364', '378263458849751040', '377385783532716032');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562365', '378263753197617152', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562366', '378263753197617152', '377385783532716032');

INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476925', '378263837368909824', '377385783062953984');
INSERT INTO `hspt_role_menu` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810436476926', '378263837368909824', '377385783532716032');

-- ----------------------------
-- Table structure for hspt_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `hspt_role_permission`;
CREATE TABLE `hspt_role_permission` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_role_permission` bigint(18) NOT NULL COMMENT '角色权限主键',
  `pk_permission` bigint(18) NOT NULL COMMENT '权限主键',
  `pk_role` bigint(18) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`pk_role_permission`),
  KEY `pk_role` (`pk_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限信息表';

-- ----------------------------
-- Records of hspt_role_permission
-- ----------------------------
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934461', '459724809119465411', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934462', '459724809119465412', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934463', '459724809119465413', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934464', '459724809119465414', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934465', '459724809119465411', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934466', '459724809119465412', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934467', '459724809119465413', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809215934468', '459724809119465414', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095231', '459724809119465421', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095232', '459724809119465422', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095233', '459724809119465423', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095234', '459724809119465424', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095235', '459724809119465421', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095236', '459724809119465422', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095237', '459724809119465423', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809392095238', '459724809119465424', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730041', '459724809119465431', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730042', '459724809119465432', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730043', '459724809119465433', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809513730044', '459724809119465434', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730045', '459724809119465431', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730046', '459724809119465432', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730047', '459724809119465433', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809513730048', '459724809119465434', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616121', '459724809119465441', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616122', '459724809119465442', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616123', '459724809119465443', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809597616124', '459724809119465444', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616125', '459724809119465441', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616126', '459724809119465442', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616127', '459724809119465443', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809597616128', '459724809119465444', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502201', '459724809119465451', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502202', '459724809119465452', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502203', '459724809119465453', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809681502204', '459724809119465454', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809681502205', '459724809119465451', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809681502206', '459724809119465452', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809681502207', '459724809119465453', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809681502208', '459724809119465454', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611071', '459724809119465461', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611072', '459724809119465462', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611073', '459724809119465463', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611074', '459724809119465464', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809748611075', '459724809119465461', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809748611076', '459724809119465462', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809748611077', '459724809119465463', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809748611078', '459724809119465464', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719931', '459724809119465473', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719932', '459724809119465474', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809815719933', '459724809119465475', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809815719934', '459724809119465473', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809815719935', '459724809119465474', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809815719936', '459724809119465475', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411711', '459724809119465481', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411712', '459724809119465482', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411713', '459724809119465483', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809895411714', '459724809119465484', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411715', '459724809119465481', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411716', '459724809119465482', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411717', '459724809119465483', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809895411718', '459724809119465484', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103481', '459724809119465491', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103482', '459724809119465492', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103483', '459724809119465493', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724809975103484', '459724809119465494', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809975103485', '459724809119465491', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809975103486', '459724809119465492', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809975103487', '459724809119465493', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724809975103488', '459724809119465494', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795261', '459724809119465501', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795262', '459724809119465502', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795263', '459724809119465503', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810054795264', '459724809119465504', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795265', '459724809119465501', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795266', '459724809119465502', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795267', '459724809119465503', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810054795268', '459724809119465504', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681341', '459724809119465511', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681342', '459724809119465512', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681343', '459724809119465513', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681344', '459724809119465514', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681345', '459724809119465511', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681346', '459724809119465512', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681347', '459724809119465513', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810138681348', '459724809119465514', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984511', '459724809119465521', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984512', '459724809119465522', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984513', '459724809119465523', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810209984514', '459724809119465524', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810209984515', '459724809119465521', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810209984516', '459724809119465522', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810209984517', '459724809119465523', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810209984518', '459724809119465524', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481981', '459724809119465531', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481982', '459724809119465532', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481983', '459724809119465533', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810285481984', '459724809119465534', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810285481985', '459724809119465531', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810285481986', '459724809119465532', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810285481987', '459724809119465533', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810285481988', '459724809119465534', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562361', '459724809119465541', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562362', '459724809119465542', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562365', '459724809119465541', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810373562366', '459724809119465542', '377385783532716032');

INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476921', '459724809119465551', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '0', '2018-06-22 14:22:16', null, null, null, '459724810436476922', '459724809119465552', '377385783062953984');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810436476925', '459724809119465551', '377385783532716032');
INSERT INTO `hspt_role_permission` VALUES ('2018-06-22 14:22:16', '1', '2018-06-22 14:22:16', null, null, null, '459724810436476926', '459724809119465552', '377385783532716032');

-- ----------------------------
-- Table structure for hspt_user
-- ----------------------------
DROP TABLE IF EXISTS `hspt_user`;
CREATE TABLE `hspt_user` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_user` bigint(18) NOT NULL COMMENT '用户主键',
  `user_code` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `user_password` char(60) DEFAULT NULL COMMENT '用户密码使用MD5双加密存储',
  `user_email` varchar(150) DEFAULT NULL COMMENT '邮箱',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `user_status` int(11) DEFAULT '0' COMMENT '用户状态 默认0 正常态 1锁定',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `user_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `user_auth` int(11) DEFAULT '0' COMMENT '认证状态 0为注册用户 1为认证用户',
  `en_key` varchar(50) DEFAULT NULL COMMENT '密码加密密钥',
  `init_user` int(11) DEFAULT '0' COMMENT '是否为初始用户，默认0为初始用户 1为非初始用户，初始用户需要初始化信息',
  `last_password_reset_date` datetime DEFAULT NULL COMMENT '最后修改密码时间',
  `pk_group` bigint(18) DEFAULT NULL COMMENT '组织主键',
  `end_time` varchar(19) DEFAULT NULL COMMENT '账号停用时间',
  PRIMARY KEY (`pk_user`),
  KEY `user_code` (`user_code`),
  KEY `user_email` (`user_email`),
  KEY `user_phone` (`user_phone`),
  KEY `pk_group` (`pk_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of hspt_user
-- ----------------------------
INSERT INTO `hspt_user` VALUES ('2018-05-15 15:51:52', '0', '2017-11-08 18:56:50', '377457784972640256', '2018-05-15 15:51:52', '377457784972640256', '377457784972640256', 'admin', '23CC827BA04603A796AD0844915C1F33', 'admin@hspt.org', '', '0', '系统管理员', '', '1', 'o3Z8yPil', '0', '2018-05-15 15:51:52', '-1', '');
INSERT INTO `hspt_user` VALUES ('2018-05-14 19:46:26', '0', '2017-11-09 14:00:33', '377457784972640256', '2018-05-14 19:46:26', '377457784972640256', '378182074307182592', 'group', '228E60403ADA3534D0700FAFC777D9F8', 'group@hspt.org', '', '0', '公共管理员', '', '1', 'KUcxNxxD', '0', '2017-11-09 14:00:33', '378188098061729792', '');
INSERT INTO `hspt_user` VALUES ('2018-02-06 11:03:24', '0', '2017-11-09 14:22:23', '378182074307182592', '2018-02-06 11:03:24', '378182074307182592', '378187570242125824', 'demo', 'A8E3B7C8443C9F3F94183DD62FE79167', 'demo@hspt.org', '', '0', '演示管理员', '', '1', 'DHKnC1PJ', '0', '2017-11-09 14:22:23', '378181361514577920', '');

-- ----------------------------
-- Table structure for hspt_user_employees
-- ----------------------------
DROP TABLE IF EXISTS `hspt_user_employees`;
CREATE TABLE `hspt_user_employees` (
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `pk_user_employees` bigint(18) NOT NULL COMMENT '用户人员主键',
  `pk_employees` bigint(18) NOT NULL COMMENT '人员主键',
  `pk_user` bigint(18) NOT NULL COMMENT '用户主键',
  PRIMARY KEY (`pk_user_employees`),
  KEY `pk_employees` (`pk_employees`),
  KEY `pk_user` (`pk_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员用户表';

-- ----------------------------
-- Records of hspt_user_employees
-- ----------------------------

-- ----------------------------
-- Table structure for hspt_user_role
-- ----------------------------
DROP TABLE IF EXISTS `hspt_user_role`;
CREATE TABLE `hspt_user_role` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` int(11) DEFAULT '0' COMMENT '状态标识 0为正常 1为删除 2为封存',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createby` bigint(18) DEFAULT NULL COMMENT '创建人',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lastmodifyby` bigint(18) DEFAULT NULL COMMENT '最后修改人',
  `user_role_id` bigint(18) NOT NULL COMMENT '用户角色主键',
  `pk_role` bigint(18) DEFAULT NULL COMMENT '角色主键',
  `pk_user` bigint(18) DEFAULT NULL COMMENT '用户主键',
  PRIMARY KEY (`user_role_id`),
  KEY `pk_user` (`pk_role`,`pk_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Records of hspt_user_role
-- ----------------------------
INSERT INTO `hspt_user_role` VALUES ('2017-11-07 14:02:29', '0', '2017-11-09 14:15:58', '377457784972640256', '2017-11-07 14:02:29', '377457784972640256', '377457785538871296', '377385783062953984', '377457784972640256');
INSERT INTO `hspt_user_role` VALUES ('2017-11-09 14:00:33', '0', '2017-11-09 14:00:33', '377457784972640256', '2017-11-09 14:00:33', '377457784972640256', '378182074638532608', '377385783532716032', '378182074307182592');
INSERT INTO `hspt_user_role` VALUES ('2017-11-09 14:22:23', '0', '2017-11-09 14:22:23', '378182074307182592', '2017-11-09 14:22:23', '378182074307182592', '378187570523144192', '377385783532716032', '378187570242125824');
