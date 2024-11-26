package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.UserMapper;
import com.chy.pojo.Car;
import com.chy.pojo.User;
import com.chy.service.CarService;
import com.chy.mapper.CarMapper;
import com.chy.service.UserService;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【car】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
    implements CarService{
    @Autowired private CarMapper carMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired private UserMapper userMapper;

    @Override
    public Result bindCarToUser(Car car, String token) {
        // 获取 token 对应的用户 ID
        int userId = jwtHelper.getUserId(token).intValue();

        if (jwtHelper.isExpiration(token)) {
            // Token 过期，直接返回未登录
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        // 查询用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.build(null, ResultCodeEnum.USER_NOT_FOUND);  // 如果用户不存在，返回 404 用户未找到
        }

        // 如果用户存在，设置车辆的 userId
        car.setUserId(userId);

        // 将车辆信息插入到数据库
        int rowsAffected = carMapper.insert(car);
        if (rowsAffected > 0) {
            return Result.ok("车辆绑定成功");  // 车辆成功绑定，返回成功信息
        } else {
            return Result.build(null, ResultCodeEnum.CREATE_FAILED);  // 绑定失败，返回创建失败信息
        }
    }

    @Override
    public Result deleteCar(String token, String carPlateNumber) {
//        user.setIsDeleted(1);  // 设置为已删除
//        userMapper.updateById(user);
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        LambdaQueryWrapper<Car> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Car::getCarPlateNumber,carPlateNumber);
        Car car = carMapper.selectOne(queryWrapper);
        if (car != null) {
            car.setIsDeleted(1);
            int rowsAffected = carMapper.updateById(car);
            return Result.build(rowsAffected, ResultCodeEnum.SUCCESS);
        }
        return Result.build(null,ResultCodeEnum.CAR_NOT_EXIST);
    }

    @Override
    public Result updateCarInfo(Car car, String token) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.UNAUTHROIZED);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        return null;
    }
}




