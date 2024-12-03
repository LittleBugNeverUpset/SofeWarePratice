package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.AdminLogs;
import com.chy.service.AdminLogsService;
import com.chy.mapper.AdminLogsMapper;
import com.chy.service.AdminService;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author littlebug
* @description 针对表【admin_logs】的数据库操作Service实现
* @createDate 2024-12-02 15:46:50
*/
@Service
public class AdminLogsServiceImpl extends ServiceImpl<AdminLogsMapper, AdminLogs>
    implements AdminLogsService{
    @Autowired
    private AdminLogsMapper adminLogsMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public void generateAdminLogs(Integer adminId, String type, String description) {
        AdminLogs adminLogs = new AdminLogs();

        adminLogs.setAdminId(adminId);
        adminLogs.setOperationType(type);
        adminLogs.setDescription(description);
        adminLogs.setTimestamp(new Date());

        adminLogsMapper.insert(adminLogs);
    }

    @Override
    public Result getAllAdminLogs(String token) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper();
        List<AdminLogs> adminLogs = adminLogsMapper.selectList(queryWrapper);
        return Result.build(adminLogs,ResultCodeEnum.SUCCESS);
//        return null;
    }
}




