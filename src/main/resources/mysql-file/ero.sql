/*
 Navicat Premium Data Transfer

 Source Server         : lcler
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : ero

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 28/01/2020 19:00:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `authority` VALUES (2, 'ROLE_USER');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK8kcum44fvpupyw6f5baccx25c`(`user_id`) USING BTREE,
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 'すごいセクシーで惜しかった美人たち', '2020-01-28 10:58:19', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tend` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe`(`email`) USING BTREE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 28, NULL, '', '510854315@qq.com', '$2a$10$6v6jGmBCG3qGaWrWLHguEeiDWuRhL0i6o.96g7I6nu6/Il73VPE2q', '男', '長身', 'lcl');
INSERT INTO `user` VALUES (2, 39, NULL, '', '734824855@qq.com', '$2a$10$NJr2UUPHoPLxgRfCVj3b5OQGaChNh35J6UsL4RuLv23zFO4oa9ydm', '女', '欧米', 'asiya');

-- ----------------------------
-- Table structure for user_authority
-- ----------------------------
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority`  (
  `user_id` int(11) NOT NULL,
  `authority_id` int(11) NOT NULL,
  INDEX `FKgvxjs381k6f48d5d2yi11uh89`(`authority_id`) USING BTREE,
  INDEX `FKpqlsjpkybgos9w2svcri7j8xy`(`user_id`) USING BTREE,
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_authority
-- ----------------------------
INSERT INTO `user_authority` VALUES (1, 1);
INSERT INTO `user_authority` VALUES (2, 2);

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_size` int(11) NULL DEFAULT NULL,
  `cover_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `images` varchar(2550) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `object` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `read_size` int(11) NULL DEFAULT NULL,
  `tags` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `video_length` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `video_qua` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vote_size` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKlvftuhj7tfoq8kigg4lc2ps7p`(`user_id`) USING BTREE,
  CONSTRAINT `FKlvftuhj7tfoq8kigg4lc2ps7p` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES (1, 1, 'cover65f4e2d5-870a-439e-a507-aa94c98989e8.jpg', '2020-01-26 07:57:47', '/uploadFiles/image2451fd56-4362-4192-8ea9-4d2dc96eb53a.jpg,/uploadFiles/image221c6b71-0411-4346-a69a-bd5fe38c0705.jpg,/uploadFiles/image97e91bb9-17f3-40bb-a76b-a284781903f0.jpg', 'straight', 3, 'blowjob', 'sdfwefwerfwerfwerf', '00:24', '360', 'E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles/video7c70add6-e8ba-4871-a280-4a17abd547a4.mp4', 1, 1);
INSERT INTO `video` VALUES (2, 0, 'cover6e9776e3-e6c2-477b-89a9-4c7e4137b6db.jpg', '2020-01-28 10:59:31', '/uploadFiles/imaged18f57e1-98d7-44b2-b6a7-c6588793cb0c.jpg,/uploadFiles/image88f504d4-5a04-4b6f-a1d0-bbfe2b6d1a35.jpg,/uploadFiles/imageef56d805-143b-4ae4-9ca0-96f2c9639972.jpg', 'gay', 0, 'europe', 'えっちな美人', '00:45', '360', 'E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles/videoc2c822bb-7f63-4157-87be-91d206d58805.mp4', 0, 1);

-- ----------------------------
-- Table structure for video_comment
-- ----------------------------
DROP TABLE IF EXISTS `video_comment`;
CREATE TABLE `video_comment`  (
  `video_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  UNIQUE INDEX `UK_k15a7vs0bl11b7q3bc88x9nw3`(`comment_id`) USING BTREE,
  INDEX `FKi9rnt9uebvtiw4clylqvukqbr`(`video_id`) USING BTREE,
  CONSTRAINT `FK7qcjc7d5kd072rch4xl5x42dr` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKi9rnt9uebvtiw4clylqvukqbr` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_comment
-- ----------------------------
INSERT INTO `video_comment` VALUES (1, 1);

-- ----------------------------
-- Table structure for video_vote
-- ----------------------------
DROP TABLE IF EXISTS `video_vote`;
CREATE TABLE `video_vote`  (
  `video_id` int(11) NOT NULL,
  `vote_id` int(11) NOT NULL,
  UNIQUE INDEX `UK_s1m2alt1wfnfwic3407a1fuf`(`vote_id`) USING BTREE,
  INDEX `FKg6h7i78otx5q60j3mavtms41m`(`video_id`) USING BTREE,
  CONSTRAINT `FKg6h7i78otx5q60j3mavtms41m` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKn5j0smce97a3yd3rcreit9em0` FOREIGN KEY (`vote_id`) REFERENCES `vote` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_vote
-- ----------------------------
INSERT INTO `video_vote` VALUES (1, 1);

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKcsaksoe2iepaj8birrmithwve`(`user_id`) USING BTREE,
  CONSTRAINT `FKcsaksoe2iepaj8birrmithwve` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vote
-- ----------------------------
INSERT INTO `vote` VALUES (1, '2020-01-28 10:57:48', 1);

SET FOREIGN_KEY_CHECKS = 1;
