package com.chy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.CaptchaMapper;
import com.chy.mapper.CarMapper;
import com.chy.mapper.UserMapper;
import com.chy.pojo.Admin;
import com.chy.pojo.Captcha;
import com.chy.pojo.Car;
import com.chy.pojo.User;
import com.chy.service.AdminLogsService;
import com.chy.service.AdminService;
import com.chy.mapper.AdminMapper;
import com.chy.service.CaptchaService;
import com.chy.utils.JwtHelper;
import com.chy.utils.MD5Util;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author littlebug
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-11-26 10:37:17
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private  JwtHelper jwtHelper;
    @Autowired
    private CaptchaMapper captchaMapper;
    @Autowired
    private AdminLogsService adminLogsService;


    @Override
    public Result login(Admin admin) {
        //根据账号查询
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAdminName , admin.getAdminName());
        Admin loginAdmin = adminMapper.selectOne(queryWrapper);
        //账号判断
        if (loginAdmin == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(admin.getAdminPassword())
                && loginAdmin.getAdminPassword().equals(MD5Util.encrypt(admin.getAdminPassword())))
        {
            //账号密码正确
            //根据用户唯一标识生成token
            Integer adminId = loginAdmin.getAdminId();
            String token = jwtHelper.createToken(Long.valueOf(loginAdmin.getAdminId()));

            Map data = new HashMap();
            data.put("token",token);
            Date date = new Date();
            adminLogsService.generateAdminLogs(adminId,"Login","Login At " +date.toString());
            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getAdminInfo(String token) {
        if(jwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }

        int adminId = jwtHelper.getUserId(token).intValue();

        Admin admin = adminMapper.selectById(adminId);
        if (admin != null) {
            admin.getAdminPassword();
            Map data = new HashMap();
            data.put("loginAdmin",admin);
            return Result.ok(admin);
        }
        return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
    }

    @Override
    public Result checkAdminName(String adminname) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAdminName,adminname);
        Admin admin = adminMapper.selectOne(queryWrapper);

        if (admin != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(Admin admin, String captcha) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAdminName,admin.getAdminName());
        Long count = adminMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        LambdaQueryWrapper<Captcha> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Captcha::getCode,captcha);
        Captcha captcha1 = captchaMapper.selectOne(queryWrapper1);
        if (captcha1 == null){
            return Result.build(null,ResultCodeEnum.INVALID_PARAMS);
        } else if (captcha1.getIsUsed() != 1) {
            admin.setAdminPassword(MD5Util.encrypt(admin.getAdminPassword()));
            admin.setAdminPermissionLevel(captcha1.getAdminLevel());
            captcha1.setIsUsed(1);

            captchaMapper.updateById(captcha1);
            int rows = adminMapper.insert(admin);
            System.out.println("rows = " + rows);
            adminLogsService.generateAdminLogs(0,"Generate Low Level Admin",admin.toString());
            return Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.INVALID_PARAMS);
    }

    @Override
    public Result getAllUsers(String token) {
        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
//        QueryWrapper<User> queryWapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(null);
        adminLogsService.generateAdminLogs(adminId,"get All Users List",users.toString());
        return Result.build(users,ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result getAllcars(String token) {
        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
//        QueryWrapper<User> queryWapper = new QueryWrapper<>();
        List<Car> cars = carMapper.selectList(null);
        adminLogsService.generateAdminLogs(adminId,"get All Cars List",cars.toString());
        return Result.build(cars,ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result generateDM(Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAdminName,admin.getAdminName());
        Long count = adminMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        else {
            admin.setAdminPassword(MD5Util.encrypt(admin.getAdminPassword()));
            admin.setAdminPermissionLevel(1);
            int rows = adminMapper.insert(admin);
            System.out.println("rows = " + rows);
            return Result.ok(null);
        }
    }

}




