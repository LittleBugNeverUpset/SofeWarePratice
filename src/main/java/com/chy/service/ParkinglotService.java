package com.chy.service;

import com.chy.pojo.Parkinglot;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

import java.util.List;

/**
* @author littlebug
* @description 针对表【parkinglot】的数据库操作Service
* @createDate 2024-11-03 23:12:14
*/
public interface ParkinglotService extends IService<Parkinglot> {

    Result addParkinglots(String token, List<Parkinglot> parkinglots);
}
