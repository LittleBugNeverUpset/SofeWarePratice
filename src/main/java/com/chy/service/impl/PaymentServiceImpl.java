package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Payment;
import com.chy.service.PaymentService;
import com.chy.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【payment】的数据库操作Service实现
* @createDate 2024-11-26 10:37:17
*/
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
    implements PaymentService{

}




