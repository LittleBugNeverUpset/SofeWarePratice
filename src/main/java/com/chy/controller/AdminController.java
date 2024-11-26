package com.chy.controller;


import com.chy.pojo.Admin;
import com.chy.pojo.Facilities;
import com.chy.pojo.ParkingSlot;
import com.chy.pojo.Parkinglot;
import com.chy.service.*;
import com.chy.service.AdminService;
import com.chy.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理员服务接口", description = "")
@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;;
    @Autowired
    FacilitiesService facilitiesService;
    @Autowired
    private ParkinglotService parkinglotService;
    @Autowired
    private ParkingSlotService parkingSlotService;

    /**
     * 管理员自身操作模块
     *
     */
    @Operation(summary = "管理员登录", description = "")
    @PostMapping("login")
    public Result login(@RequestBody Admin admin){
        Result result = adminService.login(admin);
        System.out.println("result = " + result);
        return result;
    }

    @Operation(summary = "登陆获取登录者信息", description = "")
    @GetMapping("getAdminInfo")
    public Result userInfo(@RequestHeader String token){
        Result result = adminService.getAdminInfo(token);
        return result;
    }

    @Operation(summary = "检查管理员名是否已经存在", description = "")
    @PostMapping("checkAdminName")
    public Result checkUserName(String adminname){
        Result result = adminService.checkAdminName(adminname);
        return result;
    }

    @Operation(summary = "注册管理员账号", description = "必须有最高级管理员授权的验证码才能够生成管理员")
    @PostMapping("regist")
    public Result regist(@RequestBody Admin admin, String verificationCode){
        Result result = adminService.regist(admin);
        return result;
    }
    @Operation(summary = "管理员批量录入停车场", description = "")
    @PostMapping("Parkinglot")
    public Result parkinglot(@RequestHeader String token, @RequestBody List<Parkinglot> parkinglots){
        Result result = parkinglotService.addParkinglots(token, parkinglots);
        return result;
    }

    @PostMapping("ParkingSlot")
    public Result parkingSlot(@RequestHeader String token, @RequestBody List<ParkingSlot> parkingSlots){
        Result result = parkingSlotService.addParkingSlots(token, parkingSlots);
        return result;
    }

    @Operation(summary = "管理员获取所有用户列表", description = "")
    @GetMapping("allUsersList")
    public Result getUsersList(@RequestHeader String token){
        Result result = adminService.getAllUsers(token);
        return result;
    }
    @Operation(summary = "管理员获取所有车辆列表", description = "")
    @GetMapping("allCarList")
    public Result getCarList(@RequestHeader String token){
        Result result = adminService.getAllcars(token);
        return result;
    }
    @Operation(summary = "管理员批量录入便民设施", description = "")
    @PostMapping("facilities")
    public Result facilities(@RequestHeader String token, List<Facilities> facilities){
        Result result = facilitiesService.addFacilities(token,facilities);
        return result;
    }

    @Operation(summary = "管理员获取所有便民设施列表", description = "")
    @GetMapping("allFacilities")
    public Result getAllFacilities(@RequestHeader String token){
        Result result = facilitiesService.getAllFacilities(token);
        return result;
    }

    /**
     * 日志模块
     */

}
