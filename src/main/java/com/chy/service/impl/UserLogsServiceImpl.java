package com.chy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.UserLogs;
import com.chy.service.UserLogsService;
import com.chy.mapper.UserLogsMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author littlebug
* @description 针对表【user_logs】的数据库操作Service实现
* @createDate 2024-12-02 15:46:50
*/
@Service
public class UserLogsServiceImpl extends ServiceImpl<UserLogsMapper, UserLogs>
    implements UserLogsService{
    @Autowired
    private UserLogsMapper userLogsMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public void generateUserLogs(Integer userId, String type, String description) {
        UserLogs userLogs = new UserLogs();
        userLogs.setTimestamp(new Date());
        userLogs.setUserId(userId);
        userLogs.setOperationType(type);
        userLogs.setDescription(description);
        userLogsMapper.insert(userLogs);
    }

    @Override
    public Result getAllUserLogs(String token) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        LambdaQueryWrapper<UserLogs> wrapper = new LambdaQueryWrapper<>();
        List<UserLogs> userLogs = userLogsMapper.selectList(wrapper.eq(UserLogs::getUserId, adminId));
        return Result.build(userLogs,ResultCodeEnum.SUCCESS);
    }
}




