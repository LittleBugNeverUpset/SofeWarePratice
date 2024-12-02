package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Facilities;
import com.chy.pojo.User;
import com.chy.service.FacilitiesService;
import com.chy.mapper.FacilitiesMapper;
import com.chy.service.UserLogsService;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author littlebug
* @description 针对表【facilities】的数据库操作Service实现
* @createDate 2024-11-26 10:37:17
*/
@Service
public class FacilitiesServiceImpl extends ServiceImpl<FacilitiesMapper, Facilities>
    implements FacilitiesService{
    @Autowired private FacilitiesMapper facilitiesMapper;
    @Autowired private JwtHelper jwtHelper;
    @Autowired private UserLogsService userLogsService;

    @Override
    public Result addFacilities(String token, Facilities facilities) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        return null;
    }

    @Override
    public Result addFacilities(String token, List<Facilities> facilitiesList) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        List<Facilities> failInsterList = new ArrayList<Facilities>();
        for (Facilities facilities : facilitiesList) {
            // 使用查询包装器，检查数据库中是否存在相同的设施
            LambdaQueryWrapper<Facilities> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Facilities::getFacilitiesName, facilities.getFacilitiesName());  // 你可以修改为其他条件，例如 ID
            long count = facilitiesMapper.selectCount(queryWrapper);

            // 如果不存在该设施，则插入
            if (count == 0) {
                facilities.setIsDeleted(0);  // 设置为未删除

                facilitiesMapper.insert(facilities);  // 插入单条数据
            } else {
                // 如果设施已经存在，可以根据需求做一些处理，比如返回错误信息
                failInsterList.add(facilities);
            }
        }
        if (failInsterList.size() > 0) {
            return Result.build(failInsterList, ResultCodeEnum.INSERT_FAILED);
        }

        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result getAllFacilities(String token) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        List<Facilities> facilities = facilitiesMapper.selectList(null);
//        userLogsService.generateUserLogs();
        return Result.build(facilities,ResultCodeEnum.SUCCESS);
    }
}




