package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.ParkingSlot;
import com.chy.pojo.Parkinglot;
import com.chy.service.ParkingSlotService;
import com.chy.mapper.ParkingSlotMapper;
import com.chy.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author littlebug
* @description 针对表【parking_slot】的数据库操作Service实现
* @createDate 2024-11-26 10:37:17
*/
@Service
public class ParkingSlotServiceImpl extends ServiceImpl<ParkingSlotMapper, ParkingSlot>
    implements ParkingSlotService{
    @Override
    public Result addParkingSlots(String token, Parkinglot parkinglot, List<ParkingSlot> parkingSlots) {
        return null;
    }
}




