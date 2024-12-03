package com.chy.service;

import com.chy.pojo.AdminLogs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【admin_logs】的数据库操作Service
* @createDate 2024-12-02 15:46:50
*/
public interface AdminLogsService extends IService<AdminLogs> {
    public void generateAdminLogs(Integer adminId,String type,String description);

    Result getAllAdminLogs(String token);
}
