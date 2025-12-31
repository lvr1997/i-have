-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.12 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 ihave 的数据库结构
CREATE DATABASE IF NOT EXISTS `ihave` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `ihave`;

-- 导出  表 ihave.address 结构
CREATE TABLE IF NOT EXISTS `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `recv_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_area` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_district` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_zip` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recv_tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_default` int(11) DEFAULT NULL,
  `created_user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `modified_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `modified_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.address 的数据：~2 rows (大约)
INSERT INTO `address` (`id`, `uid`, `recv_name`, `recv_province`, `recv_city`, `recv_area`, `recv_district`, `recv_addr`, `recv_phone`, `recv_tel`, `recv_zip`, `recv_tag`, `is_default`, `created_user`, `created_time`, `modified_user`, `modified_time`) VALUES
	(1, 1, '西瓜', '23', '01', '09', '黑龙江省哈尔滨市松北区', '黑龙江科技大学9#421', '15124680346', '', '150022', '宿舍', 1, NULL, NULL, NULL, NULL),
	(2, 3, 'lr', '23', '01', '09', '黑龙江省哈尔滨市松北区', '宿舍', '17645653758', '', '150022', '宿舍', 1, NULL, NULL, NULL, NULL);

-- 导出  表 ihave.carousel 结构
CREATE TABLE IF NOT EXISTS `carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号 主键',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '轮播图标题',
  `create_at` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：1显示 0隐藏',
  `descript` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '轮播图描述文字',
  `img_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '轮播图图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.carousel 的数据：~2 rows (大约)
INSERT INTO `carousel` (`id`, `title`, `create_at`, `status`, `descript`, `img_url`) VALUES
	(1, '毕业季', '2019-05-23', 1, '毕业季，二手随心发', 'banner1.png'),
	(2, '新概念图书', '2019-05-23', 1, '欢迎各位发布一些闲置的书籍', 'banner1.jpg'),
	(3, '毕业季毕业季', '2019-05-23', 1, '毕业季', 'banner2.png');

-- 导出  表 ihave.category 结构
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号 主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1可用  0不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.category 的数据：~6 rows (大约)
INSERT INTO `category` (`id`, `name`, `status`) VALUES
	(1, '电子数码', 1),
	(2, '棋牌休闲', 1),
	(3, '服装衣物', 1),
	(4, '书籍刊物', 1),
	(5, '其他', 0),
	(7, 'Filter Bags', 0);

-- 导出  表 ihave.comments 结构
CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `user_id` int(11) NOT NULL COMMENT '评论人用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `create_at` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论时间',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '评论内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.comments 的数据：~2 rows (大约)
INSERT INTO `comments` (`id`, `user_id`, `goods_id`, `create_at`, `content`) VALUES
	(1, 1, 1, '2019-05-19', 'hello'),
	(2, 1, 1, '2019-05-19', '你好呀，初来乍到');

-- 导出  表 ihave.goods 结构
CREATE TABLE IF NOT EXISTS `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `catelog_id` int(11) NOT NULL COMMENT '所属分类',
  `user_id` int(11) NOT NULL COMMENT '发布人',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `price` double NOT NULL COMMENT '出售价格',
  `real_price` double DEFAULT NULL COMMENT '原购买价格',
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上架时间',
  `polish_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发布时间',
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '下架时间',
  `first_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品首图',
  `phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人手机号',
  `good_buy_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易方式',
  `good_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易地址',
  `view_count` int(11) DEFAULT NULL COMMENT '商品发布数量',
  `describle` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品描述',
  `status` tinyint(4) DEFAULT '1' COMMENT '商品状态',
  `seller_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品备注（仅发布者自己可见）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.goods 的数据：~2 rows (大约)
INSERT INTO `goods` (`id`, `catelog_id`, `user_id`, `name`, `price`, `real_price`, `start_time`, `polish_time`, `end_time`, `first_image`, `phone_number`, `good_buy_method`, `good_address`, `view_count`, `describle`, `status`, `seller_note`) VALUES
	(1, 1, 1, 'Lenovo笔记本', 1000, 3999, '2019-05-19', '2019-05-20', NULL, NULL, '15124680346', '3', '9舍', 13, 'Lenovo笔记本  固态硬盘128', 0, NULL),
	(2, 1, 3, 'oppor9s手机', 800, 1399, '2019-05-23', '2019-05-23', NULL, NULL, '13019711506', '3', '学生公寓', 8, 'oppor9s手机，用了一年', 3, NULL);

-- 导出  表 ihave.image 结构
CREATE TABLE IF NOT EXISTS `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号 主键',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `img_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.image 的数据：~2 rows (大约)
INSERT INTO `image` (`id`, `goods_id`, `img_url`) VALUES
	(1, 1, '14dad59a-4ff8-4ad9-a556-bb92ffecb90b.png'),
	(2, 2, '17f7fb26-e785-4a5b-b82a-6c7d2c8d345f.jpg');

-- 导出  表 ihave.notice 结构
CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻公告表主键',
  `user_id` int(11) DEFAULT NULL COMMENT '发布人id ',
  `create_at` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：0不显示 1显示',
  `context` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '新闻公告主图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.notice 的数据：~0 rows (大约)

-- 导出  表 ihave.orders 结构
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号，系统根据订单时间生成',
  `user_id` int(11) NOT NULL COMMENT '购买用户id',
  `seller_id` int(11) DEFAULT NULL COMMENT '卖家用户id',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易地址',
  `pay_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付码',
  `create_at` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '创建时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1未完成 买家购买后订单为未完成的状态 \r\n2已完成  卖家在点击确认发货后 订单为已完成的状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.orders 的数据：~0 rows (大约)
INSERT INTO `orders` (`id`, `order_id`, `user_id`, `seller_id`, `good_id`, `address`, `pay_id`, `create_at`, `status`) VALUES
	(1, '20190528172406559', 1, NULL, 2, '1', '20190528172308624', '2019-05-28', 1);

-- 导出  表 ihave.posts 结构
CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thumb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  ihave.posts 的数据：~0 rows (大约)
INSERT INTO `posts` (`id`, `title`, `author`, `content`, `thumb_url`, `type`, `create_time`, `update_time`) VALUES
	(1, '修改标题', 'lrrr', '测试内容test', '', 1, '2022-12-11 16:00:00', '2024-09-06 08:22:12');

-- 导出  表 ihave.purse 结构
CREATE TABLE IF NOT EXISTS `purse` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '钱包表主键编号',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `balance` double(16,2) NOT NULL COMMENT '余额',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态：1：可用，2：不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- 正在导出表  ihave.purse 的数据：~0 rows (大约)

-- 导出  表 ihave.reply 结构
CREATE TABLE IF NOT EXISTS `reply` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `atuser_id` int(11) DEFAULT NULL,
  `commet_id` int(11) DEFAULT NULL,
  `create_at` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.reply 的数据：~0 rows (大约)

-- 导出  表 ihave.report 结构
CREATE TABLE IF NOT EXISTS `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 编号',
  `type` int(11) NOT NULL COMMENT '类型：1投诉，2反馈建议',
  `good_id` int(11) DEFAULT NULL COMMENT '商品id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0未处理，1已处理，-1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.report 的数据：~0 rows (大约)

-- 导出  表 ihave.roles 结构
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL COMMENT 'id',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色标识',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `commet` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_id` (`role_id`) USING BTREE COMMENT '角色id唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- 正在导出表  ihave.roles 的数据：~4 rows (大约)
INSERT INTO `roles` (`id`, `role_id`, `role_name`, `commet`, `create_date`) VALUES
	(1, 'STUDENT', '学生用户', '学生角色，用登录学生端系统', '20220222'),
	(2, 'ADMIN', '系统管理员', '配置学校信息', '20220222'),
	(3, 'AUDITOR', '审核员', '用户审核学生发布的物品信息', '20220222'),
	(4, 'SUPERMANAGER', '超级管理员', '具有全部操作权限', '20220222');

-- 导出  表 ihave.school 结构
CREATE TABLE IF NOT EXISTS `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `school_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学校编码（系统随机生成8位字符串）',
  `school_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学校名称',
  `school_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学校位置',
  `create_at` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `school_id` (`school_id`) USING BTREE COMMENT '学校编码唯一'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- 正在导出表  ihave.school 的数据：~3 rows (大约)
INSERT INTO `school` (`id`, `school_id`, `school_name`, `school_location`, `create_at`) VALUES
	(1, '00120000', '黑龙江科技大学', NULL, '20220222'),
	(2, '00120001', '哈尔滨工程大学', NULL, '20220222'),
	(3, '00120002', '哈尔滨理工大学', NULL, '20220222');

-- 导出  表 ihave.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动递增',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id，唯一标识',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户手机号',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `create_at` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `last_login` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上次登录时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '用户状态',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生日',
  `sex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `img_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '头像',
  `residence` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '简介',
  `signature` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '个性签名',
  `role_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'STUDENT' COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id_indexq` (`user_id`) USING BTREE COMMENT '用户编号唯一'
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.user 的数据：~8 rows (大约)
INSERT INTO `user` (`id`, `user_id`, `phone`, `username`, `password`, `create_at`, `last_login`, `status`, `birthday`, `sex`, `img_url`, `residence`, `signature`, `role_id`) VALUES
	(1, 'aasdds', '15124680346', '测试用户AAA', '900150983CD24FB0D6963F7D28E17F72', '2019-05-19', '2019-06-03', 1, '', '女', 'default.png', '', '', 'STUDENT'),
	(2, 'werefdsf', '17611056916', '测试用户bbbb', '47BCE5C74F589F4867DBD57E9CA9F808', '2019-05-19', '2020-10-12', 1, '2019-05-19', '男', 'default.png', NULL, NULL, 'STUDENT'),
	(3, 'fdsdfsd', '13019711506', '西瓜味的小仙', 'E10ADC3949BA59ABBE56E057F20F883E', '2019-05-23', '2019-05-23', 1, '1989-05-26', '男', 'default.png', '黑龙江-哈尔滨市-松北区', '', 'STUDENT'),
	(4, 'fdsff', '15232103749', 'AlineSerily', 'E10ADC3949BA59ABBE56E057F20F883E', '2020-04-14', '2021-12-05', 1, '1998-04-09', '男', 'default.png', '黑龙江-哈尔滨市-南岗区', '', 'STUDENT'),
	(5, 'dghfgh', '13703111234', '詹三_审核员', 'E10ADC3949BA59ABBE56E057F20F883E', '2020-12-23', NULL, 1, '1998-07-12', '男', 'default.png', NULL, NULL, 'STUDENT'),
	(6, 'fghgjgjgu', '17765432111', '张三_系统管理员', 'E10ADC3949BA59ABBE56E057F20F883E', '2020-12-22', NULL, 1, NULL, '男', 'default.png', NULL, NULL, 'STUDENT'),
	(7, 'fdfrhh', '13207111234', '李四_超级管理员', 'E10ADC3949BA59ABBE56E057F20F883E', '2020-12-23', NULL, 1, NULL, '男', 'default.png', NULL, NULL, 'STUDENT'),
	(8, '085889', '132****1234', 'test', 'E10ADC3949BA59ABBE56E057F20F883E', '2025-12-24', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ADMIN');

-- 导出  表 ihave.wanted 结构
CREATE TABLE IF NOT EXISTS `wanted` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏表 主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `good_id` int(11) DEFAULT NULL COMMENT '收藏商品id',
  `create_at` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态：0取消收藏，1收藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  ihave.wanted 的数据：~0 rows (大约)
INSERT INTO `wanted` (`id`, `user_id`, `good_id`, `create_at`, `status`) VALUES
	(1, 4, 1, '2020-09-20', NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
