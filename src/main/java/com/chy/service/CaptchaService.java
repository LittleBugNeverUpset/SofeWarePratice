package com.chy.service;

import com.chy.pojo.Captcha;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.utils.Result;

/**
* @author littlebug
* @description 针对表【captcha】的数据库操作Service
* @createDate 2024-11-27 15:50:00
*/
public interface CaptchaService extends IService<Captcha> {

    Result generatecaptcha(String token, Integer adminLevel);
}
