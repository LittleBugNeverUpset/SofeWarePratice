package com.chy.service;

import com.chy.pojo.Payment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【payment】的数据库操作Service
* @createDate 2024-11-28 11:33:13
*/
public interface PaymentService extends IService<Payment> {

    Result payOrder(String token);
}
