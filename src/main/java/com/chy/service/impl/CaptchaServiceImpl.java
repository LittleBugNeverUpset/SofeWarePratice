package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Captcha;
import com.chy.service.CaptchaService;
import com.chy.mapper.CaptchaMapper;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【captcha】的数据库操作Service实现
* @createDate 2024-11-26 14:54:24
*/
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha>
    implements CaptchaService{

}




