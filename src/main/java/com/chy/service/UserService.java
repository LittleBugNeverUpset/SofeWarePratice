package com.chy.service;

import com.chy.pojo.Car;
import com.chy.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【user】的数据库操作Service
* @createDate 2024-11-03 23:12:14
*/
public interface UserService extends IService<User> {

    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);

//    Result getAllUsers(String token);

    Result updateUserInfo(User user,String token);

    Result checkUserEmail(String email);


    Result deleteUserAccount(String token);
}
