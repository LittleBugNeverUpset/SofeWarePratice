package com.chy.service;

import com.chy.pojo.Master;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【master】的数据库操作Service
* @createDate 2024-11-03 23:12:14
*/
public interface MasterService extends IService<Master> {
    Result login(Master master);

    Result getMasterInfo(String token);

    Result checkMasterName(String mastername);

    Result regist(Master master);

    Result getAllUsers(String token);
}
