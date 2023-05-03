/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/04/2023 16:57:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for booklist
-- ----------------------------
DROP TABLE IF EXISTS `booklist`;
CREATE TABLE `booklist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groups` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `press` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `price` double(10, 2) NOT NULL DEFAULT 0.00,
  `quantity` int(5) NOT NULL DEFAULT 0,
  `isbn` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booklist
-- ----------------------------
INSERT INTO `booklist` VALUES (1, '地理', '房龙地理', '房龙', '文汇出版社', 29.00, 9, '9780000000001');
INSERT INTO `booklist` VALUES (2, '地理', '地理学与生活', '[美] 阿瑟·格蒂斯 ', '世界图书出版公司', 49.00, 9, '9780000000002');
INSERT INTO `booklist` VALUES (3, '地理', '古老阳光的末日', 'Thom Hartmann', '上海远东出版社', 20.00, 9, '9780000000003');
INSERT INTO `booklist` VALUES (4, '法律', '洞穴奇案', '[美] 萨伯', '生活.读书.新知三联书店', 18.00, 9, '9780000000004');
INSERT INTO `booklist` VALUES (5, '法律', '西窗法雨', '刘星', '法律出版社', 24.00, 9, '9780000000005');
INSERT INTO `booklist` VALUES (6, '法律', '审判为什么不公正', '[英] 卡德里', '新星出版社', 49.50, 9, '9780000000006');
INSERT INTO `booklist` VALUES (7, '军事', '亮剑（舒适阅读版）', '都梁', '北京联合出版公司', 45.00, 9, '9780000000007');
INSERT INTO `booklist` VALUES (8, '军事', '二战记忆 安妮日记', '[德] 安妮·弗兰克', '人民文学出版社', 23.00, 9, '9780000000008');
INSERT INTO `booklist` VALUES (9, '军事', '亮剑', '都梁', '解放军文艺出版社', 25.00, 9, '9780000000009');
INSERT INTO `booklist` VALUES (10, '历史', '历史是什么？', '爱德华·霍列特·卡尔', '商务印书馆', 19.00, 9, '9780000000010');
INSERT INTO `booklist` VALUES (11, '历史', '中国史学史', '金毓黻', '商务印书馆', 19.00, 9, '9780000000011');
INSERT INTO `booklist` VALUES (12, '历史', '史记选', '[清] 储欣', '商务印书馆', 48.00, 9, '9780000000012');
INSERT INTO `booklist` VALUES (13, '计算机', 'Java从入门到精通 ', '明日科技', '清华大学出版社', 69.00, 9, '9780000000013');
INSERT INTO `booklist` VALUES (14, '计算机', 'C++从入门到精通', '李伟明', '清华大学出版社', 49.00, 9, '9780000000014');
INSERT INTO `booklist` VALUES (15, '计算机', 'PHP从入门到精通', '千锋教育高教产品研发部', '清华大学出版社', 59.00, 9, '9780000000015');

-- ----------------------------
-- Table structure for borrowlist
-- ----------------------------
DROP TABLE IF EXISTS `borrowlist`;
CREATE TABLE `borrowlist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `isbn` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `id_card` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `borrow_time` datetime(0) NOT NULL DEFAULT '2023-04-15 08:00:00',
  `return_time` datetime(0) NOT NULL DEFAULT '2023-04-15 08:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowlist
-- ----------------------------
INSERT INTO `borrowlist` VALUES (1, '房龙地理', '9780000000001', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (2, '地理学与生活', '9780000000002', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (3, '古老阳光的末日', '9780000000003', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (4, '洞穴奇案', '9780000000004', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (5, '西窗法雨', '9780000000005', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (6, '审判为什么不公正', '9780000000006', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (7, '亮剑（舒适阅读版）', '9780000000007', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (8, '二战记忆 安妮日记', '9780000000008', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (9, '亮剑', '9780000000009', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (10, '历史是什么？', '9780000000010', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (11, '中国史学史', '9780000000011', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (12, '史记选', '9780000000012', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (13, 'Java从入门到精通 ', '9780000000013', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (14, 'C++从入门到精通', '9780000000014', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');
INSERT INTO `borrowlist` VALUES (15, 'PHP从入门到精通', '9780000000015', '张一', '23010001', '13700000001', '2023-04-15 08:00:00', '2023-04-15 08:00:00');

-- ----------------------------
-- Table structure for operationlist
-- ----------------------------
DROP TABLE IF EXISTS `operationlist`;
CREATE TABLE `operationlist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime(0) NOT NULL DEFAULT '2023-04-15 08:00:00',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operationlist
-- ----------------------------
INSERT INTO `operationlist` VALUES (1, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (2, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (3, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (4, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (5, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (6, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (7, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (8, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (9, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (10, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (11, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (12, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (13, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (14, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (15, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (16, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (17, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (18, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (19, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');
INSERT INTO `operationlist` VALUES (20, '2023-04-15 08:00:00', '张一', '房龙地理', '借走了 房龙地理 书籍');

-- ----------------------------
-- Table structure for typelist
-- ----------------------------
DROP TABLE IF EXISTS `typelist`;
CREATE TABLE `typelist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of typelist
-- ----------------------------
INSERT INTO `typelist` VALUES (1, '地理', '10001');
INSERT INTO `typelist` VALUES (2, '法律', '10002');
INSERT INTO `typelist` VALUES (3, '军事', '10003');
INSERT INTO `typelist` VALUES (4, '历史', '10004');
INSERT INTO `typelist` VALUES (5, '计算机', '10005');

-- ----------------------------
-- Table structure for userlist
-- ----------------------------
DROP TABLE IF EXISTS `userlist`;
CREATE TABLE `userlist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groups` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `id_card` int(8) NOT NULL DEFAULT 0,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `identity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '学生',
  `book_count` int(255) NOT NULL DEFAULT 3,
  `state` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userlist
-- ----------------------------
INSERT INTO `userlist` VALUES (1, 'admin', '管理员', 'Admin', '123456', '男', 23010000, '13700000000', '管理员', 3, 1);
INSERT INTO `userlist` VALUES (2, 'user', '张一', '张一', '123456', '男', 23010001, '13700000001', '学生', 3, 1);
INSERT INTO `userlist` VALUES (3, 'user', '张二', '张二', '123456', '男', 23010002, '13700000002', '学生', 3, 1);
INSERT INTO `userlist` VALUES (4, 'user', '张三', '张三', '123456', '男', 23010003, '13700000003', '学生', 3, 1);
INSERT INTO `userlist` VALUES (5, 'user', '张四', '张四', '123456', '男', 23010004, '13700000004', '学生', 3, 1);
INSERT INTO `userlist` VALUES (6, 'user', '张五', '张五', '123456', '男', 23010005, '13700000005', '学生', 3, 1);
INSERT INTO `userlist` VALUES (7, 'user', '李一', '李一', '123456', '男', 23010006, '13700000006', '老师', 3, 1);
INSERT INTO `userlist` VALUES (8, 'user', '李二', '李二', '123456', '男', 23010007, '13700000007', '老师', 3, 1);
INSERT INTO `userlist` VALUES (9, 'user', '李三', '李三', '123456', '男', 23010008, '13700000008', '老师', 3, 1);
INSERT INTO `userlist` VALUES (10, 'user', '李四', '李四', '123456', '男', 23010009, '13700000009', '老师', 3, 1);
INSERT INTO `userlist` VALUES (11, 'user', '李五', '李五', '123456', '男', 23010010, '13700000010', '老师', 3, 1);
INSERT INTO `userlist` VALUES (12, 'user', '赵一', '赵一', '123456', '男', 23010011, '13700000011', '学生', 3, 1);
INSERT INTO `userlist` VALUES (13, 'user', '赵二', '赵二', '123456', '男', 23010012, '13700000012', '学生', 3, 1);
INSERT INTO `userlist` VALUES (14, 'user', '赵三', '赵三', '123456', '男', 23010013, '13700000013', '学生', 3, 1);
INSERT INTO `userlist` VALUES (15, 'user', '赵四', '赵四', '123456', '男', 23010014, '13700000014', '学生', 3, 1);
INSERT INTO `userlist` VALUES (16, 'user', '赵五', '赵五', '123456', '男', 23010015, '13700000015', '学生', 3, 1);

SET FOREIGN_KEY_CHECKS = 1;
