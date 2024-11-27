package com.chy.service;

import com.chy.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【admin】的数据库操作Service
* @createDate 2024-11-26 10:37:17
*/
public interface AdminService extends IService<Admin> {

    Result login(Admin admin);

    Result getAdminInfo(String token);

    Result checkAdminName(String adminname);

    Result regist(Admin admin, String captcha);

    Result getAllUsers(String token);

    Result getAllcars(String token);
}
