-- Empty demo schema. Original rows and database credentials are excluded.
SET NAMES utf8mb4;

CREATE TABLE `cus`  (
  `cusid` int(0) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户电话',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'qq号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'email',
  `unit1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一级单位',
  `unit2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '二级单位',
  `num` int(0) NULL DEFAULT NULL COMMENT '成交数',
  `discount` float NULL DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`cusid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `file`  (
  `fileid` int(0) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `proid` int(0) NULL DEFAULT NULL COMMENT '项目id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `extName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '文件夹' COMMENT '文件后缀名',
  `upperfileid` int(0) NULL DEFAULT NULL COMMENT '上一级文件id',
  `starttime` date NULL DEFAULT NULL COMMENT '创建时间',
  `staffid` int(0) NULL DEFAULT NULL COMMENT '员工id',
  PRIMARY KEY (`fileid`) USING BTREE,
  INDEX `proid`(`proid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `monitor`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '监控id',
  `staffid` int(0) NULL DEFAULT NULL COMMENT '员工id ',
  `processname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '进程名',
  `usetime` bigint(0) NULL DEFAULT NULL COMMENT '使用时间戳差',
  `cpu` float(10, 2) NULL DEFAULT NULL COMMENT 'cpu占用率',
  `pid` int(0) NULL DEFAULT NULL COMMENT '进程pid',
  `starttime` bigint(0) NULL DEFAULT NULL COMMENT '开始时间戳',
  `checkTime` datetime(0) NULL DEFAULT NULL COMMENT '检测时间',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staffid`(`staffid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `project`  (
  `fileid` int(0) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
  `mxid` int(0) NULL DEFAULT NULL COMMENT '模型主管id',
  `xrid` int(0) NULL DEFAULT NULL COMMENT '渲染主管id',
  `hqid` int(0) NULL DEFAULT NULL COMMENT '后期主管id',
  `state` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目状态,0:模型未分配，1:模型正在执行1,2:模型完成，渲染未分配',
  `cusid` int(0) NULL DEFAULT NULL COMMENT '顾客id',
  `starttime` date NULL DEFAULT NULL COMMENT '开始时间',
  `endtime` date NULL DEFAULT NULL COMMENT '结束时间',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `realprice` float(10, 2) NULL DEFAULT NULL COMMENT '实际价格',
  `extname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '文件夹' COMMENT '文件后缀',
  PRIMARY KEY (`fileid`) USING BTREE,
  INDEX `mxid`(`mxid`) USING BTREE,
  INDEX `xrid`(`xrid`) USING BTREE,
  INDEX `hqid`(`hqid`) USING BTREE,
  INDEX `cusid`(`cusid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `reason`  (
  `remakeid` int(0) NOT NULL AUTO_INCREMENT COMMENT '打回编号',
  `staproid` int(0) NULL DEFAULT NULL COMMENT '员工项目表编号',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '打回原因',
  `time` date NULL DEFAULT NULL COMMENT '打回时间',
  PRIMARY KEY (`remakeid`) USING BTREE,
  INDEX `staproid`(`staproid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `staff`  (
  `staffid` int(0) NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `position` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `department` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `num` int(0) NULL DEFAULT 0 COMMENT '工作量',
  `upperid` int(0) NULL DEFAULT 0 COMMENT '主管id',
  `workingnum` int(0) NULL DEFAULT 0 COMMENT '任务量',
  PRIMARY KEY (`staffid`) USING BTREE,
  INDEX `upperid`(`upperid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `stapro`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '员工项目id',
  `staffid` int(0) NULL DEFAULT NULL COMMENT '员工id',
  `proid` int(0) NULL DEFAULT NULL COMMENT '项目id',
  `state` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '0：未接受，1：正在执行，2：正在拒绝,3:打回后项目回收，4：完成',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staffid2`(`staffid`) USING BTREE,
  INDEX `proid2`(`proid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


CREATE TABLE `usestaff`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `staffid` int(0) NOT NULL COMMENT '使用者id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主机名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staffid`(`staffid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;
