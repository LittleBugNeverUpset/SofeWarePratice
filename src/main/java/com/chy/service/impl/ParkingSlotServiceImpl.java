package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.ParkinglotMapper;
import com.chy.pojo.ParkingSlot;
import com.chy.pojo.Parkinglot;
import com.chy.service.ParkingSlotService;
import com.chy.mapper.ParkingSlotMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author littlebug
* @description 针对表【parking_slot】的数据库操作Service实现
* @createDate 2024-11-28 12:22:12
*/
@Service
public class ParkingSlotServiceImpl extends ServiceImpl<ParkingSlotMapper, ParkingSlot>
    implements ParkingSlotService{
    @Autowired
    private ParkingSlotMapper parkingSlotMapper;
    @Autowired
    private ParkinglotMapper parkinglotMapper;
    @Autowired
    private JwtHelper jwtHelper;


    @Override
    public Result addParkingSlots(String token, Integer parkinglotID, List<ParkingSlot> parkingSlots) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        List<ParkingSlot> failInsertList = new ArrayList<ParkingSlot>();
        for (ParkingSlot parkingSlot : parkingSlots) {
            LambdaQueryWrapper<Parkinglot> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Parkinglot::getParkinglotId, parkinglotID);
            long count = parkinglotMapper.selectCount(queryWrapper);

            // 如果停车场不存在，返回错误信息
            if (count == 0) {
                return Result.build(null, ResultCodeEnum.PARKINGLOT_NOT_FOUND);
            }
            // 使用查询包装器，检查数据库中是否已存在相同的停车位
            LambdaQueryWrapper<ParkingSlot> slotQueryWrapper = new LambdaQueryWrapper<>();
            slotQueryWrapper.eq(ParkingSlot::getParkingslotId, parkingSlot.getParkingslotId())
                    .eq(ParkingSlot::getParkinglotId, parkinglotID); // 假设 ParkingSlot 类有停车位号和停车场 ID 字段
            long slotCount = parkingSlotMapper.selectCount(slotQueryWrapper);

            // 如果停车位不存在，进行插入
            if (slotCount == 0) {
                parkingSlot.setParkinglotId(parkinglotID); // 设置停车场 ID 关联停车位
                parkingSlot.setIsDeleted(0);  // 设置为未删除
                parkingSlotMapper.insert(parkingSlot);  // 插入单条数据
            } else {
                // 如果停车位已存在，可以根据需求做一些处理，例如记录失败的停车位
                failInsertList.add(parkingSlot);
            }
        }
        // 如果有插入失败的停车位，返回失败信息
        if (failInsertList.size() > 0) {
            return Result.build(failInsertList, ResultCodeEnum.INSERT_FAILED);
        }

        // 如果所有停车位插入成功，返回成功信息
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}




