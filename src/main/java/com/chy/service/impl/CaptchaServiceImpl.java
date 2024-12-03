package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.mapper.AdminMapper;
import com.chy.pojo.Admin;
import com.chy.pojo.Captcha;
import com.chy.service.AdminLogsService;
import com.chy.service.CaptchaService;
import com.chy.mapper.CaptchaMapper;
import com.chy.utils.JwtHelper;
import com.chy.utils.Result;
import com.chy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
* @author littlebug
* @description 针对表【captcha】的数据库操作Service实现
* @createDate 2024-11-27 15:50:00
*/
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha>
    implements CaptchaService{
    @Autowired
    private CaptchaMapper captchaMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AdminLogsService adminLogsService;


    @Override
    public Result generatecaptcha(String token, Integer adminLevel) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        }
        int adminId = jwtHelper.getUserId(token).intValue();
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            return Result.build(null, ResultCodeEnum.UNAUTHROIZED);
        } else if (admin.getAdminPermissionLevel() != 1) {
            return Result.build(null, ResultCodeEnum.LOW_PERMOSSIONS);
        } else if (adminLevel < 1 || adminLevel > 4) {
            return Result.build(null, ResultCodeEnum.LEVEL_OUT_OF_BOUNDS);

        }
        String captchaCode = UUID.randomUUID().toString().substring(0, 6);  // 6位验证码
        Captcha captcha = new Captcha();
        captcha.setCode(captchaCode);
        captcha.setGeneratedByAdminId(adminId);  // 假设由管理员 ID = 1 生成
        captcha.setAdminLevel(adminLevel);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        captcha.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(120)));              // 设置过期时间为120分钟
        adminLogsService.generateAdminLogs(adminId,"Generate Admin Regist Captcha",captchaCode.toString());
        captcha.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        captchaMapper.insert(captcha);
        return Result.build(captcha, ResultCodeEnum.SUCCESS);
    }

}




