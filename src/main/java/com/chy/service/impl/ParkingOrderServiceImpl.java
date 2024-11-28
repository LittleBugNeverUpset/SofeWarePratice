package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.dobj.OrderInitializationRequest;
import com.chy.mapper.CarMapper;
import com.chy.pojo.Car;
import com.chy.pojo.ParkingOrder;
import com.chy.service.ParkingOrderService;
import com.chy.mapper.ParkingOrderMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
* @author littlebug
* @description 针对表【parking_order】的数据库操作Service实现
* @createDate 2024-11-28 13:37:10
*/
@Service
public class ParkingOrderServiceImpl extends ServiceImpl<ParkingOrderMapper, ParkingOrder>
    implements ParkingOrderService{

    @Autowired
    private ParkingOrderMapper parkingOrderMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private CarMapper carMapper;


    @Override
    public Result initOrder(OrderInitializationRequest orderInitializationRequest, String token) {
        // 获取 token 对应的用户 ID
        int userId = jwtHelper.getUserId(token).intValue();
        if (jwtHelper.isExpiration(token)) {
            // Token 过期，直接返回未登录
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }

        LambdaQueryWrapper<Car> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Car::getCarPlateNumber,orderInitializationRequest.getCarPlateNumber());
        Car car = carMapper.selectOne(queryWrapper);
        if(car==null){
//            车辆不在系统中登记无法生成订单
            return Result.build(null, ResultCodeEnum.VEHICLE_NOT_REGISTERED);
        } else if (car.getParkingStatus() == 1) {
//            显示车辆已经在停车场中停泊
            return Result.build(null, ResultCodeEnum.VEHICLE_ALREADY_PARKING);
        }
        else {
            ParkingOrder parkingOrder = new ParkingOrder();
            parkingOrder.setUserId(userId);
            parkingOrder.setCarId(car.getCarId());
            parkingOrder.setOrderUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            parkingOrder.setParkinglotId(orderInitializationRequest.getParkinglotId());
            parkingOrder.setSlotId(orderInitializationRequest.getSlotId());
            parkingOrder.setOrderStatus(1);
            parkingOrder.setOrderCreateTime(Timestamp.valueOf(LocalDateTime.now()));
            parkingOrderMapper.insert(parkingOrder);
            return  Result.build(parkingOrder, ResultCodeEnum.SUCCESS);
        }

    }
}




