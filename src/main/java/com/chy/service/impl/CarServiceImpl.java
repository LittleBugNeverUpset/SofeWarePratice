package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Car;
import com.chy.service.CarService;
import com.chy.mapper.CarMapper;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【car】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
    implements CarService{

}




