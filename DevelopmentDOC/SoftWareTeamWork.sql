DROP DATABASE IF EXISTS `parkinglot_management`;
CREATE DATABASE `parkinglot_management`;
USE `parkinglot_management`;

-- 用户表
CREATE TABLE `user` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_name` VARCHAR(100) NOT NULL,
    `user_password` VARCHAR(255) NOT NULL COMMENT '加密存储',
    `user_sex` BOOLEAN DEFAULT NULL,
    `user_email` VARCHAR(100) NOT NULL UNIQUE,
    `user_create_time` DATETIME NOT NULL,
    `user_icon` VARCHAR(500) DEFAULT NULL COMMENT '存储头像 URL',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志'
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 停车场表
CREATE TABLE `parkinglot` (
    `parkinglot_id` INT PRIMARY KEY AUTO_INCREMENT,
    `district_id` INT NOT NULL COMMENT '关联区域表',
    `parkinglot_name` VARCHAR(100) NOT NULL,
    `parkinglot_location` POINT NOT NULL COMMENT '地理位置',
    `parkinglot_capacity` INT NOT NULL COMMENT '停车位总数',
    `parkinglot_price` DECIMAL(10, 2) NOT NULL COMMENT '基础收费',
    `parkinglot_open_time` TIME NOT NULL,
    `parkinglot_close_time` TIME NOT NULL,
    `is_free` BOOLEAN DEFAULT 0 COMMENT '是否免费',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 停车位表
CREATE TABLE `parking_slot` (
    `slot_id` INT PRIMARY KEY AUTO_INCREMENT,
    `parkinglot_id` INT NOT NULL COMMENT '所属停车场',
    `slot_status` ENUM('available', 'occupied', 'reserved') NOT NULL DEFAULT 'available',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
    FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 订单表
CREATE TABLE `order` (
    `order_id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_status` ENUM('pending', 'paid', 'cancelled') NOT NULL,
    `order_start_time` DATETIME NOT NULL,
    `order_end_time` DATETIME DEFAULT NULL,
    `parkinglot_id` INT NOT NULL COMMENT '关联停车场',
    `slot_id` INT NOT NULL COMMENT '关联停车位',
    `user_id` INT NOT NULL COMMENT '关联用户',
    `order_value` DECIMAL(10, 2) DEFAULT NULL COMMENT '订单金额',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0,
    FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE,
    FOREIGN KEY (`slot_id`) REFERENCES `parking_slot` (`slot_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 管理员表
CREATE TABLE `admin` (
    `admin_id` INT PRIMARY KEY AUTO_INCREMENT,
    `admin_account` VARCHAR(50) NOT NULL UNIQUE,
    `admin_name` VARCHAR(100) NOT NULL,
    `admin_password` VARCHAR(255) NOT NULL COMMENT '加密存储',
    `admin_permission_level` TINYINT DEFAULT 3 COMMENT '权限等级',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志'
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 区域表
CREATE TABLE `district` (
    `district_id` INT PRIMARY KEY AUTO_INCREMENT,
    `city_name` VARCHAR(100) NOT NULL COMMENT '城市名称',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志'
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 评论表
CREATE TABLE `comment` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `comment_content` TEXT NOT NULL,
    `parent_comment_id` INT DEFAULT NULL COMMENT '父评论 ID',
    `user_id` INT NOT NULL COMMENT '评论用户',
    `parkinglot_id` INT NOT NULL COMMENT '评论所属停车场',
    `rating` TINYINT DEFAULT NULL COMMENT '评分 1-5',
    `comment_time` DATETIME NOT NULL,
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 设施表
CREATE TABLE `facilities` (
    `facilities_id` INT PRIMARY KEY AUTO_INCREMENT,
    `parkinglot_id` INT NOT NULL COMMENT '所属停车场',
    `facilities_name` VARCHAR(100) NOT NULL,
    `facilities_type` ENUM('charging', 'restroom', 'security') NOT NULL COMMENT '设施类型',
    `facilities_coordinates` POINT NOT NULL COMMENT '地理位置',
    `facilities_status` ENUM('active', 'maintenance', 'inactive') DEFAULT 'active',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
    FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`parkinglot_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 车辆表
CREATE TABLE `car` (
    `car_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL COMMENT '车辆所属用户',
    `car_plate_number` VARCHAR(50) NOT NULL COMMENT '车牌号',
    `parking_status` BOOLEAN DEFAULT FALSE COMMENT '是否停入',
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 支付记录表
CREATE TABLE `payment` (
    `payment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT NOT NULL COMMENT '关联订单',
    `payment_amount` DECIMAL(10, 2) NOT NULL,
    `payment_method` ENUM('credit_card', 'wechat', 'alipay', 'cash') NOT NULL COMMENT '支付方式',
    `payment_time` DATETIME NOT NULL,
    `version` INT DEFAULT 1 COMMENT '乐观锁',
    `is_deleted` BOOLEAN DEFAULT 0 COMMENT '逻辑删除标志',
    FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 生成验证码表格
CREATE TABLE `captcha` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `code` VARCHAR(100) NOT NULL,           -- 存储验证码
   `generated_by_admin_id` INT NOT NULL,   -- 生成验证码的管理员ID
   `expiration_time` DATETIME NOT NULL,    -- 验证码过期时间
   `is_used` BOOLEAN DEFAULT FALSE,        -- 标记验证码是否已使用
   `create_time` DATETIME NOT NULL         -- 验证码创建时间
);
