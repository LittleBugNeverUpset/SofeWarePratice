package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Parkinglot;
import com.chy.service.ParkinglotService;
import com.chy.mapper.ParkinglotMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author littlebug
* @description 针对表【parkinglot】的数据库操作Service实现
* @createDate 2024-11-28 13:00:43
*/
@Service
public class ParkinglotServiceImpl extends ServiceImpl<ParkinglotMapper, Parkinglot>
    implements ParkinglotService{
    @Autowired
    private ParkinglotMapper parkinglotMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result addParkinglots(String token, List<Parkinglot> parkinglots) {
        // 校验 token 是否过期
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }

        // 获取当前管理员的 ID
        int adminId = jwtHelper.getUserId(token).intValue();

        // 用于存储插入失败的停车场
        List<Parkinglot> failInsertList = new ArrayList<Parkinglot>();

        // 遍历停车场列表，逐一插入
        for (Parkinglot parkinglot : parkinglots) {
            // 使用查询包装器，检查数据库中是否已存在相同的停车场
            LambdaQueryWrapper<Parkinglot> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Parkinglot::getParkinglotName, parkinglot.getParkinglotName());  // 可根据需求修改为其他字段，如 parkinglotId 等
            long count = parkinglotMapper.selectCount(queryWrapper);

            // 如果停车场不存在，进行插入
            if (count == 0) {
                parkinglot.setIsDeleted(0);  // 设置为未删除
                parkinglotMapper.insert(parkinglot);  // 插入单条数据
            } else {
                // 如果停车场已存在，可以根据需求做一些处理，例如记录失败的停车场
                failInsertList.add(parkinglot);
            }
        }
        // 如果有插入失败的停车场，返回失败信息
        if (failInsertList.size() > 0) {
            return Result.build(failInsertList, ResultCodeEnum.INSERT_FAILED);
        }
        // 如果所有停车场插入成功，返回成功信息
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}




