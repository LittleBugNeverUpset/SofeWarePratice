-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: parkinglot_management
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
                         `admin_id` int NOT NULL AUTO_INCREMENT,
                         `admin_account` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
                         `admin_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                         `admin_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '加密存储',
                         `admin_permission_level` tinyint DEFAULT '3' COMMENT '权限等级',
                         `version` int DEFAULT '1' COMMENT '乐观锁',
                         `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                         PRIMARY KEY (`admin_id`),
                         UNIQUE KEY `admin_account` (`admin_account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `captcha`
--

DROP TABLE IF EXISTS `captcha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `captcha` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `code` varchar(100) NOT NULL,
                           `generated_by_admin_id` int NOT NULL,
                           `admin_level` int NOT NULL,
                           `expiration_time` datetime NOT NULL,
                           `is_used` tinyint(1) DEFAULT '0',
                           `create_time` datetime NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
                       `car_id` int NOT NULL AUTO_INCREMENT,
                       `user_id` int NOT NULL COMMENT '车辆所属用户',
                       `car_plate_number` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '车牌号',
                       `parking_status` tinyint(1) DEFAULT '0' COMMENT '是否停入',
                       `version` int DEFAULT '1' COMMENT '乐观锁',
                       `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                       PRIMARY KEY (`car_id`),
                       KEY `user_id` (`user_id`),
                       CONSTRAINT `car_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
                           `comment_id` int NOT NULL AUTO_INCREMENT,
                           `comment_content` text COLLATE utf8mb4_general_ci NOT NULL,
                           `parent_comment_id` int DEFAULT NULL COMMENT '父评论 ID',
                           `user_id` int NOT NULL COMMENT '评论用户',
                           `parkinglot_id` int NOT NULL COMMENT '评论所属停车场',
                           `rating` tinyint DEFAULT NULL COMMENT '评分 1-5',
                           `comment_time` datetime NOT NULL,
                           `version` int DEFAULT '1' COMMENT '乐观锁',
                           `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                           PRIMARY KEY (`comment_id`),
                           KEY `user_id` (`user_id`),
                           KEY `parkinglot_id` (`parkinglot_id`),
                           CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
                           CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `completed_order`
--

DROP TABLE IF EXISTS `completed_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `completed_order` (
                                   `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                                   `user_id` int NOT NULL COMMENT '用户ID',
                                   `car_id` int NOT NULL COMMENT '车辆ID',
                                   `parkinglot_id` int NOT NULL COMMENT '停车场ID',
                                   `slot_id` int NOT NULL COMMENT '停车位ID',
                                   `order_status` int NOT NULL COMMENT '订单状态：1-进行中，2-完成未支付，3-完成已支付，4-已取消',
                                   `order_value` decimal(10,2) NOT NULL COMMENT '订单金额',
                                   `payment_method` int NOT NULL COMMENT '支付方式：1-微信支付，2-支付宝，3-银行卡',
                                   `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                                   `order_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
                                   `order_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后更新时间',
                                   `start_time` datetime NOT NULL COMMENT '停车开始时间',
                                   `end_time` datetime DEFAULT NULL COMMENT '停车结束时间',
                                   `duration_minutes` int DEFAULT NULL COMMENT '停车时长（分钟）',
                                   `remarks` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单备注',
                                   `total_amount` decimal(10,2) NOT NULL COMMENT '支付总金额',
                                   `is_paid` tinyint(1) DEFAULT '0' COMMENT '支付标志：0-未支付，1-已支付',
                                   `version` int DEFAULT '1' COMMENT '乐观锁',
                                   `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                                   PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district` (
                            `district_id` int NOT NULL AUTO_INCREMENT,
                            `city_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '城市名称',
                            `version` int DEFAULT '1' COMMENT '乐观锁',
                            `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                            PRIMARY KEY (`district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `facilities`
--

DROP TABLE IF EXISTS `facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities` (
                              `facilities_id` int NOT NULL AUTO_INCREMENT,
                              `parkinglot_id` int NOT NULL COMMENT '所属停车场',
                              `facilities_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                              `facilities_type` int NOT NULL COMMENT '设施类型:1-娱乐实施，2-便民服务站，3-公共卫生设施',
                              `facilities_coordinates` point NOT NULL COMMENT '地理位置',
                              `facilities_status` enum('active','maintenance','inactive') COLLATE utf8mb4_general_ci DEFAULT 'active',
                              `version` int DEFAULT '1' COMMENT '乐观锁',
                              `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                              PRIMARY KEY (`facilities_id`),
                              KEY `parkinglot_id` (`parkinglot_id`),
                              CONSTRAINT `facilities_ibfk_1` FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parking_order`
--

DROP TABLE IF EXISTS `parking_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking_order` (
                                 `order_id` int NOT NULL AUTO_INCREMENT,
                                 `order_status` int NOT NULL COMMENT '订单状态：1-进行中，2-完成未支付，3-完成已支付，4-已取消',
                                 `order_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
                                 `order_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后更新时间',
                                 `parkinglot_id` int NOT NULL COMMENT '关联停车场',
                                 `slot_id` int NOT NULL COMMENT '关联停车位',
                                 `user_id` int NOT NULL COMMENT '关联用户',
                                 `car_id` int NOT NULL COMMENT '车辆ID',
                                 `version` int DEFAULT '1' COMMENT '乐观锁',
                                 `is_deleted` tinyint(1) DEFAULT '0',
                                 PRIMARY KEY (`order_id`),
                                 KEY `parkinglot_id` (`parkinglot_id`),
                                 KEY `slot_id` (`slot_id`),
                                 KEY `user_id` (`user_id`),
                                 CONSTRAINT `parking_order_ibfk_1` FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE,
                                 CONSTRAINT `parking_order_ibfk_2` FOREIGN KEY (`slot_id`) REFERENCES `parking_slot` (`slot_id`) ON DELETE CASCADE,
                                 CONSTRAINT `parking_order_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parking_slot`
--

DROP TABLE IF EXISTS `parking_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking_slot` (
                                `slot_id` int NOT NULL AUTO_INCREMENT,
                                `parkingslot_id` int NOT NULL COMMENT '停车场中的停车位号',
                                `parkinglot_id` int NOT NULL COMMENT '所属停车场',
                                `slot_status` int NOT NULL DEFAULT '1' COMMENT '停车位类型：1-可使用。2-正在被使用，3-已关闭',
                                `version` int DEFAULT '1' COMMENT '乐观锁',
                                `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                                PRIMARY KEY (`slot_id`),
                                KEY `parkinglot_id` (`parkinglot_id`),
                                CONSTRAINT `parking_slot_ibfk_1` FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parkinglot`
--

DROP TABLE IF EXISTS `parkinglot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkinglot` (
                              `parkinglot_id` int NOT NULL AUTO_INCREMENT,
                              `district_id` int NOT NULL COMMENT '关联区域表',
                              `parkinglot_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                              `parkinglot_capacity` int NOT NULL COMMENT '停车位总数',
                              `parkinglot_price` decimal(10,2) NOT NULL COMMENT '基础收费',
                              `parkinglot_open_time` time NOT NULL,
                              `parkinglot_close_time` time NOT NULL,
                              `is_free` tinyint(1) DEFAULT '0' COMMENT '是否免费',
                              `version` int DEFAULT '1' COMMENT '乐观锁',
                              `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                              PRIMARY KEY (`parkinglot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
                           `payment_id` int NOT NULL AUTO_INCREMENT,
                           `order_id` int NOT NULL COMMENT '关联订单',
                           `total_amount` decimal(10,2) NOT NULL COMMENT '支付总金额',
                           `payment_method` int NOT NULL COMMENT '支付方式：1-微信支付，2-支付宝，3-银行卡',
                           `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                           `version` int DEFAULT '1' COMMENT '乐观锁',
                           `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                           PRIMARY KEY (`payment_id`),
                           KEY `order_id` (`order_id`),
                           CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `parking_order` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `user_id` int NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                        `user_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '加密存储',
                        `user_sex` tinyint(1) DEFAULT NULL,
                        `user_email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                        `user_create_time` datetime NOT NULL,
                        `user_icon` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '存储头像 URL',
                        `version` int DEFAULT '1' COMMENT '乐观锁',
                        `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'parkinglot_management'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-28 14:06:42
