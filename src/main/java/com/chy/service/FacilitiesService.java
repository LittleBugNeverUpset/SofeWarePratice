package com.chy.service;

import com.chy.pojo.Facilities;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

import java.util.List;

/**
* @author littlebug
* @description 针对表【facilities】的数据库操作Service
* @createDate 2024-11-26 10:37:17
*/
public interface FacilitiesService extends IService<Facilities> {

    Result addFacilities(String token, Facilities facilities) ;
    Result addFacilities(String token, List<Facilities> facilities) ;

    Result getAllFacilities(String token);

    Result deleteFacilitiesNyId(String token, int facilitiesId);
}
