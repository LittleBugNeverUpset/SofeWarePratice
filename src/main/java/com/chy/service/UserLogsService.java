package com.chy.service;

import com.chy.pojo.UserLogs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【user_logs】的数据库操作Service
* @createDate 2024-12-02 15:46:50
*/
public interface UserLogsService extends IService<UserLogs> {
    public void generateUserLogs(Integer userId,String type,String description);

    Result getAllUserLogs(String token);
}
