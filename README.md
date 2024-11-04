# SofeWarePratice

### DataBase used in this project

``` SQL
DROP TABLE IF EXISTS `parkinglot_managent`;
CREATE DATABASE `parkinglot_managent`;
USE `parkinglot_managent`;

CREATE TABLE `user`(
    `user_id` INT PRIMARY KEY NOT NULL ,
    `user_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `user_password` VARCHAR(30) NOT NULL ,
    `user_sex` BOOLEAN DEFAULT NULL,
    `user_email` VARCHAR(20) NOT NULL,
    `user_create_time` DATETIME NOT NULL ,
    `user_icon` VARCHAR(1000) DEFAULT NULL,
    `version` INT DEFAULT  1 COMMENT '乐观锁',
    `is_deleted` INT DEFAULT 0
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `parkinglot`(
    `parkinglot_id` INT NOT NULL ,
    `parkinglot_district_id` INT NOT NULL,
    `parkinglot_distract_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `parkinglot_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `parkinglot_location` POINT NOT NULL,
    `parkinglot_number` INT NOT NULL,
    `parkinglot_price` DOUBLE NOT NULL ,
    `parkinglot_open_time` TIME NOT NULL,
    `parkinglot_end_time` TIME NOT NULL ,
    `is_free` INT DEFAULT 0,
    `version` INT DEFAULT  1 COMMENT '乐观锁',
    `is_deleted` INT DEFAULT 0,
    PRIMARY KEY (`parkinglot_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `order`(
    `order_id` INT NOT NULL,
    `order_status` INT NOT NULL,
    `order_start_time` DATETIME NOT NULL ,
    `order_end_time` DATETIME DEFAULT NULL,
    `parkinglot_id` INT NOT NULL,
    `car_id` INT NOT NULL,
    `user_id` INT,
    `order_value` DOUBLE DEFAULT NULL,
    `version` INT DEFAULT  1 COMMENT '乐观锁',
    `is_deleted` INT DEFAULT 0,
    PRIMARY KEY (`order_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `master`(
    `master_id` INT NOT NULL ,
    `master_account` VARCHAR(30) NOT NULL ,
    `master_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `master_password` VARCHAR(30) NOT NULL ,
    `master_permission_level` INT DEFAULT 3,
    `version` INT DEFAULT  1 COMMENT '乐观锁',
    `is_deleted` INT DEFAULT 0,
    PRIMARY KEY (`master_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;



CREATE TABLE `car`(
    `car_id` INT NOT NULL ,
    `user_id` INT NOT NULL ,
    `parking_status` BOOLEAN DEFAULT FALSE,
    `version` INT DEFAULT  1 COMMENT '乐观锁',
    `is_deleted` INT DEFAULT 0,
    PRIMARY KEY (`car_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `distract`(
    `distract_id`  INT NOT NULL ,
    `city_id` INT NULL,
    `city_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `is_deleted` INT DEFAULT 0,
    PRIMARY KEY (`distract_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `user` (user_id, user_name, user_password, user_sex, user_email, user_create_time) VALUES (1,'littlebug','123456',TRUE,'08225155@cumt.edu.cn','2017-07-25 19:41:14');

alter table user change `user_password` `user_password` VARCHAR(100) NOT NULL ;

alter table `user` change `user_id` `user_id` INT NOT NULL AUTO_INCREMENT   ;
```