package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.*;
import com.chy.pojo.*;
import com.chy.service.CompletedOrderService;
import com.chy.service.PaymentService;
import com.chy.service.UserLogsService;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
* @author littlebug
* @description 针对表【payment】的数据库操作Service实现
* @createDate 2024-11-28 11:33:13
*/
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
    implements PaymentService{
    @Autowired
    private ParkingOrderMapper parkingOrderMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private CompletedOrderMapper completedOrderMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private ParkinglotMapper parkinglotMapper;
    @Autowired
    private UserLogsService userLogsService;

    @Override
    public Result payOrder(String token) {
        // 获取 token 对应的用户 ID
        int userId = jwtHelper.getUserId(token).intValue();
        if (jwtHelper.isExpiration(token)) {
            // Token 过期，直接返回未登录
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        LambdaQueryWrapper<ParkingOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkingOrder::getUserId,userId);
        queryWrapper.eq(ParkingOrder::getOrderStatus,2);
        if(parkingOrderMapper.selectCount(queryWrapper) == 0 ){
            return Result.build(null, ResultCodeEnum.ORDER_ERROR);
        }else {
            Date now = Timestamp.valueOf(LocalDateTime.now());
            ParkingOrder parkingOrder = parkingOrderMapper.selectOne(queryWrapper);
            parkingOrder.setOrderUpdateTime(now);
            LambdaQueryWrapper<Car> carQueryWrapper = new LambdaQueryWrapper<>();
            carQueryWrapper.eq(Car::getCarId,parkingOrder.getCarId());
            Car car = carMapper.selectOne(carQueryWrapper);
            LambdaQueryWrapper<Parkinglot> parkinglotQueryWrapper = new LambdaQueryWrapper<>();
            parkinglotQueryWrapper.eq(Parkinglot::getParkinglotId,parkingOrder.getParkinglotId());
            Parkinglot parkinglot = parkinglotMapper.selectOne(parkinglotQueryWrapper);
            parkingOrder.setOrderStatus(3);
            CompletedOrder completedOrder = new CompletedOrder();
            completedOrder.setOrderId(parkingOrder.getOrderId());
            completedOrder.setUserId(userId);
            completedOrder.setCarId(car.getCarId());
            completedOrder.setParkinglotId(parkingOrder.getParkinglotId());
            completedOrder.setSlotId(parkingOrder.getSlotId());
            Date currentDate = new Date();
            Double parkingTime = (currentDate.getTime() - parkingOrder.getOrderCreateTime().getTime())/(double) (60 * 60 * 1000);
            Double parkinglotPrice =parkinglot.getParkinglotPrice().doubleValue() ;
            completedOrder.setOrderValue(BigDecimal.valueOf(parkinglotPrice * parkingTime));
            completedOrder.setPaymentMethod(1);
            completedOrder.setPaymentTime(now);
            completedOrder.setOrderUpdateTime(now);
            completedOrder.setStartTime(parkingOrder.getOrderCreateTime());
            completedOrder.setEndTime(parkingOrder.getOrderCreateTime());
            int durationtime = (int)(currentDate.getTime() - parkingOrder.getOrderCreateTime().getTime())/ (60 * 1000);
            completedOrder.setDurationMinutes(durationtime);
            completedOrder.setRemarks("");
            completedOrder.setTotalAmount(BigDecimal.valueOf(parkinglotPrice * parkingTime));
            completedOrder.setIsPaid(1);
            completedOrder.setOrderStatus(2);
            completedOrder.setOrderId(parkingOrder.getOrderId());
            completedOrder.setOrderCreateTime(now);
            completedOrder.setOrderUpdateTime(now);
            userLogsService.generateUserLogs(userId,"Paid and Complete Order",  completedOrder.toString());
            completedOrderMapper.insert(completedOrder);
            parkingOrderMapper.updateById(parkingOrder);
            return Result.build(completedOrder  +"Order has been paid", ResultCodeEnum.SUCCESS);
        }
    }
}




