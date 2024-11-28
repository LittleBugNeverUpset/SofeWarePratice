package com.chy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Car;
import com.chy.pojo.User;
import com.chy.service.UserService;
import com.chy.mapper.UserMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.MD5Util;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
* @author littlebug
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private  UserMapper userMapper;

    /**
     * 登录业务实现
     * @param user
     * @return result封装
     */
    @Override
    public Result login(User user) {

        //根据账号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User loginUser = userMapper.selectOne(queryWrapper);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getUserPassword())
                && loginUser.getUserPassword().equals(MD5Util.encrypt(user.getUserPassword())))
        {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUserId()));

            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }
    /**
     * 查询用户数据
     * @param token
     * @return result封装
     */
    @Override
    public Result getUserInfo(String token) {

        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }

        //2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();

        //3.查询数据
        User user = userMapper.selectById(userId);

        if (user != null) {
            user.getUserPassword();
            Map data = new HashMap();
            data.put("loginUser",user);
            return Result.ok(data);
        }

        return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
    }
    /**
     * 检查账号是否可以注册
     *
     * @param username 账号信息
     * @return
     */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPassword(MD5Util.encrypt(user.getUserPassword()));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }


    @Override
    public Result updateUserInfo(User user, String token) {
        // 获取 token 对应的用户 ID
        int userId = jwtHelper.getUserId(token).intValue();
        if (jwtHelper.isExpiration(token)) {
            // Token 过期，直接返回未登录
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        if (user == null || user.getUserId() == null) {
            return Result.build(null, ResultCodeEnum.INVALID_PARAMS);
        }

        // 校验 userId 是否一致，确保用户只能更新自己的信息
//        if (userId != user.getUserId()) {
//            return Result.build(null, ResultCodeEnum.FORBIDDEN); // 不允许更新其他用户信息
//        }
        user.setUserId(userId);

        // 这里可以检查用户是否更改了密码，如果没有更改，不更新密码
        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
            // 进行密码加密等操作
        }

        // 使用 LambdaQueryWrapper 进行条件更新
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, userId); // 只更新当前用户的信息

        // 执行更新
        int updateCount = userMapper.update(user, queryWrapper);
        if (updateCount == 0) {
            return Result.build(null, ResultCodeEnum.UPDATE_FIELD_FAILED); // 如果没有记录被更新
        }

        // 获取更新后的用户信息
        User updatedUser = userMapper.selectById(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("loginUser", updatedUser);

        return Result.ok(data);
    }

    @Override
    public Result checkUserEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail,email);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return Result.build(null,ResultCodeEnum.USEREMAIL_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result deleteUserAccount(String token) {
        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }

        //2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();

        //3.查询数据
        User user = userMapper.selectById(userId);

        if (user != null) {
            user.setIsDeleted(1);  // 设置为已删除
            userMapper.updateById(user);
            return Result.ok(user);
        }

        return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
    }


}




