package com.chy.controller;

import com.chy.pojo.Master;
import com.chy.pojo.User;
import com.chy.service.MasterService;
import com.chy.service.UserService;
import com.chy.service.impl.UserServiceImpl;
import com.chy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("master")
@CrossOrigin
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private UserService userService;;

    @PostMapping("login")
    public Result login(@RequestBody Master master){
        Result result = masterService.login(master);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("getMasterInfo")
    public Result userInfo(@RequestHeader String token){
        Result result = masterService.getMasterInfo(token);
        return result;
    }

    @PostMapping("checkMasterName")
    public Result checkUserName(String mastername){
        Result result = masterService.checkMasterName(mastername);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody Master master){
        Result result = masterService.regist(master);
        return result;
    }

    /**
     *
     * @param token
     * @return UserList
     * 反回所有User
     */
    @GetMapping("getUsersList")
    public Result getUsersList(@RequestHeader String token){
       Result result = masterService.getAllUsers(token);
       return result;
    }
//    @PostMapping("getUsersList")
}
