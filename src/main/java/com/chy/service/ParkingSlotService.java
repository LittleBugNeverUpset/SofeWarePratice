package com.chy.service;

import com.chy.pojo.ParkingSlot;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

import java.util.List;

/**
* @author littlebug
* @description 针对表【parking_slot】的数据库操作Service
* @createDate 2024-11-26 10:37:17
*/
public interface ParkingSlotService extends IService<ParkingSlot> {

    Result addParkingSlots(String token, List<ParkingSlot> parkingSlots);
}
