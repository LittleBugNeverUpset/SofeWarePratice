package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Parkinglot;
import com.chy.service.ParkinglotService;
import com.chy.mapper.ParkinglotMapper;
import com.chy.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author littlebug
* @description 针对表【parkinglot】的数据库操作Service实现
* @createDate 2024-11-03 23:12:14
*/
@Service
public class ParkinglotServiceImpl extends ServiceImpl<ParkinglotMapper, Parkinglot>
    implements ParkinglotService{

    @Override
    public Result addParkinglots(String token, List<Parkinglot> parkinglots) {
        return null;
    }
}




