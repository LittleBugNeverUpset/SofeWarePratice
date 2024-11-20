# SofeWarePratice(Parkinglot Management System)

### DataBase Used in This Troject

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

### Interface Between Front and Backend 
#### UserController
##### Regist
Request Method: **POST** 

Request URL:"http://localhost:8080/user/regist"
``` json
{
    "userName":"littlebug1", //用户名
    "userPassword":"123456",     //明文密码
    "userEmail":"3185153802@qq.com",
    "userSex":"1",
    "userCreateTime":"2024-11-01T12:00:00.000+08:00"
}
```
Response
```json
{
    "code": 200,
    "message": "success",
    "data": null
}
```
##### Login
Request Method: **POST**

Request URL: "http://localhost:8080/user/login"
```json
{
    "userName":"littlebug",     //用户名
    "userPassword":"123456"     //明文密码
}
```
Response
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDc2MDewNDU20lEqLU4t8kxRsjKqBQBToAFqLwAAAA.jaIyV9pN0d2TjP_oM2pel0EIhE427mNwgcVVxisc9vlVnyuFtig8njPtFryf4G92Dl66o-7pPUe1fdRA22foDA"
    }
}
```
##### CheckUserName
Request Method: **POST**
Request URL: "http://localhost:8080/user/checkUserName"
``` bash
http://localhost:8080/user/checkUserName?username=littlebug1
```

Response
``` json
{
    "code": 505,
    "message": "userNameUsed",
    "data": null
}
```

##### getUserInfo

Request Method: **GET**

Request URL :"http://localhost:8080/user/getUserInfo"

``` JSON
HEADER: "token":"eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDc2MjQyNTU21VEqLU4t8kxRsjKqBQBwX6QeLwAAAA.Pv2F7Rt-eKTCeLkccWD79yCWi4O6IWWGoklEDJa0fLckHlhk-OgHxYDv_m_YqKuo6P-dtYHUsfG3s3KSigdpyw"
```

Response
``` json
{
    "code": 200,
    "message": "success",
    "data": {
        "loginUser": {
            "userId": 2,
            "userName": "littlebug1",
            "userPassword": "e10adc3949ba59abbe56e057f20f883e",
            "userSex": 1,
            "userEmail": "3185153802@qq.com",
            "userCreateTime": "2024-11-01T04:00:00.000+00:00",
            "userIcon": null,
            "version": 1,
            "isDeleted": 0
        }
    }
}
```

##### update

Request Method: **PUT**
Request URL: ""

``` json

{
    "userName":"littlebug1", //用户名
    "userPassword":"123456",     //明文密码
    "userEmail":"493457392@qq.com",
    "userSex":"1",
    "userCreateTime":"2024-11-01T12:00:00.000+08:00"
}
HEADER: "token":"eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDc2MjQyNTU21VEqLU4t8kxRsjKqBQBwX6QeLwAAAA.Pv2F7Rt-eKTCeLkccWD79yCWi4O6IWWGoklEDJa0fLckHlhk-OgHxYDv_m_YqKuo6P-dtYHUsfG3s3KSigdpyw"

```

Response

``` JSON
{
    "code": 200,
    "message": "success",
    "data": {
        "loginUser": {
            "userId": null,
            "userName": "littlebug1",
            "userPassword": "123456",
            "userSex": 1,
            "userEmail": "493457392@qq.com",
            "userCreateTime": "2024-11-01T04:00:00.000+00:00",
            "userIcon": null,
            "version": null,
            "isDeleted": null
        }
    }
}
```


#### MasterController

##### Login
##### Regist
##### getUserList