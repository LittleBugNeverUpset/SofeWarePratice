package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Order;
import com.chy.service.OrderService;
import com.chy.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【order】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




