package com.chy.service;

import com.chy.dobj.OrderInitializationRequest;
import com.chy.pojo.ParkingOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【parking_order】的数据库操作Service
* @createDate 2024-11-28 13:37:10
*/
public interface ParkingOrderService extends IService<ParkingOrder> {

    Result initOrder(OrderInitializationRequest orderInitializationRequest, String token);

    Result updateOrder(String token);

    Result cancleOrder(String token);
}
