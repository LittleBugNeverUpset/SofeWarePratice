package com.chy.service;

import com.chy.pojo.Car;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【car】的数据库操作Service
* @createDate 2024-11-03 23:12:14
*/
public interface CarService extends IService<Car> {

    Result bindCarToUser(Car car, String token);

    Result deleteCar(String token, String carPlateNumber);

    Result updateCarInfo(Car car, String token);
}
