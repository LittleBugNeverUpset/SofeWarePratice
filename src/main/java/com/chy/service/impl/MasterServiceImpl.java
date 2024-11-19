package com.chy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.UserMapper;
import com.chy.pojo.Master;
import com.chy.pojo.User;
import com.chy.service.MasterService;
import com.chy.mapper.MasterMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.MD5Util;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author littlebug
* @description 针对表【master】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class MasterServiceImpl extends ServiceImpl<MasterMapper, Master>
    implements MasterService{

    @Autowired
    private  MasterMapper masterMapper;
    @Autowired
    private  UserMapper userMapper;
    private final JwtHelper jwtHelper;

    public MasterServiceImpl(MasterMapper masterMapper, JwtHelper jwtHelper) {
        this.masterMapper = masterMapper;
        this.jwtHelper = jwtHelper;
    }

    /**
     * 登录业务实现
     * @param master
     * @return result封装
     */
    @Override
    public Result login(Master master) {
        //根据账号查询
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUserName,user.getUserName());
        LambdaQueryWrapper<Master> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Master::getMasterName , master.getMasterName());
//        User loginUser = userMapper.selectOne(queryWrapper);
        Master loginMaster = masterMapper.selectOne(queryWrapper);
        //账号判断
        if (loginMaster == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(master.getMasterPassword())
                && loginMaster.getMasterPassword().equals(MD5Util.encrypt(master.getMasterPassword())))
        {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginMaster.getMasterId()));

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
    public Result getMasterInfo(String token) {
        if(jwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }

        int masterId = jwtHelper.getUserId(token).intValue();

        Master master = masterMapper.selectById(masterId);
        if (master != null) {
            master.getMasterPassword();
            Map data = new HashMap();
            data.put("loginMaster",master);
            return Result.ok(master);
        }
        return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
    }
    /**
     * 检查账号是否可以注册
     *
     * @param mastername 账号信息
     * @return
     */

    @Override
    public Result checkMasterName(String mastername) {
        LambdaQueryWrapper<Master> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Master::getMasterName,mastername);
        Master master = masterMapper.selectOne(queryWrapper);

        if (master != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }


    @Override
    public Result regist(Master master) {
        LambdaQueryWrapper<Master> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Master::getMasterName,master.getMasterName());
        Long count = masterMapper.selectCount(queryWrapper);

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        master.setMasterPassword(MD5Util.encrypt(master.getMasterPassword()));
        int rows = masterMapper.insert(master);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }

    @Override
    public Result getAllUsers(String token) {
        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }
//        QueryWrapper<User> queryWapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(null);
        return Result.build(users,ResultCodeEnum.SUCCESS);
    }
}




