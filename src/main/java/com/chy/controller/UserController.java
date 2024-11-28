package com.chy.controller;

import com.chy.dobj.OrderInitializationRequest;
import com.chy.pojo.*;
import com.chy.service.*;
import com.chy.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Tag(name = "用户服务接口", description = "")
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private FacilitiesService facilitiesService;
    @Autowired
    private ParkingOrderService parkingOrderService;
    @Autowired
    private CompletedOrderService completedOrderService;
    @Autowired
    private PaymentService paymentService;

    /**
     * User自身操作模块
     *
     */

    @Operation(summary = "检查用户名称是否已经使用(已实现)", description = "检查用户名称是否已经使用，根据返回结果选择是否在前端拦截请求（已实现）")
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }
    @Operation(summary = "检查用户邮箱是否已经使用(已实现)", description = "检查用户邮箱是否已经使用，根据返回结果选择是否在前端拦截请求（已实现）")
    @PostMapping("checkUserEmail")
    public Result checkUserEmail(String email){
        Result result = userService.checkUserEmail(email);
        return result;
    }
    @Operation(summary = "用户注册接口(已实现)", description = "（已实现）")
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    @Operation(summary = "用户登录并获取token(已实现)", description = "获取到的token是后面用户操作的身份凭证，请获取后保持保留（已实现）")
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    @Operation(summary = "登陆成功使用token获取本用户的相关信息(已实现)", description = "（已实现）")
    @GetMapping("getUserInfo")
    public Result userInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }
    @Operation(summary = "用户更新自己的信息(已实现)", description = "不需要更新的信息需要使用getUserInfo获取用户信息将不更新的信息填充进去（已实现）")
    @PutMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody User user,@RequestHeader String token){
        Result result = userService.updateUserInfo(user,token);
        return result;
    }

    @Operation(summary = "用户销号删除自己的信息(已实现)", description = "建议前端设置确认页面让用户思考清楚（已实现）")
    @DeleteMapping("account")
    public Result deleteAccount(String token){
        Result result = userService.deleteUserAccount(token);
        return result;
    }

    /**
     *User获取服务模块
     */

    @Operation(summary = "录入车辆绑定用户(已实现)", description = "（已实现）")
    @PostMapping("car")
    public Result addVehicle(@RequestBody Car car, @RequestHeader String token){
        Result result = carService.bindCarToUser(car, token);
        return result;
    }

    @Operation(summary = "更该车辆信息(已实现)", description = "不修改的字段为null，不能修改ID（已实现）")
    @PutMapping("car")
    public Result updateCar(@RequestBody Car car, @RequestHeader String token){
        Result result = carService.updateCarInfo(car,token);
        return result;
    }

    @Operation(summary = "解除用户与车辆的绑定关系(已实现)", description = "（已实现）")
    @DeleteMapping("car")
    public Result deleteCar(@RequestHeader String token,String carPlateNumber){
        Result result = carService.deleteCar(token,carPlateNumber);
        return result;
    }

    @Operation(summary = "获取所有便民设施列表(已实现)", description = "（已实现）")
    @GetMapping("allFacilities")
    public Result getAllFacilities(String token){
        Result result = facilitiesService.getAllFacilities(token);
        return result;
    }

    /**
     * 订单模块模块
     */
    @Operation(summary = "生成初始订单(已实现)", description = "")
    @PostMapping("order")
    public Result addOrder(@RequestBody OrderInitializationRequest orderInitializationRequest, @RequestHeader String token){
        Result result = parkingOrderService.initOrder(orderInitializationRequest, token);
        return null;
    }
//
    @Operation(summary = "业务过程中更新订单", description = "（未实现）")
    @PutMapping("order")
    public Result updateOrder(@RequestBody OrderInitializationRequest orderInitializationRequest, @RequestHeader String token){
        return  null;
    }

    @Operation(summary = "业务过程中更新订单（未实现）", description = "取消订单")
    @PutMapping("cancleOrder")
    public Result cancleOrder(@RequestHeader String token){
        return null;
    }
    @Operation(summary = "更新订单状态（未实现）", description = "更新订单状态")
    @PostMapping("orderPayment")
    public Result orderPayment( @RequestHeader String token){
//        Result result = paymentService.payOrder(token);
        return null;
    }
    @Operation(summary = "流程结束生成完整订单（未实现）", description = "订单进入完成待支付状态且完成支付后调用，修改订单信息为完成且生成完整订单")
    @PutMapping("finishedOrder")
    public Result finishedOrder(@RequestHeader String token){
        return null;
    }
//    public Result updateFinishedOrder(@RequestBody Order order, @RequestHeader String token){
//        return null;
//    }
//
//    @GetMapping("completedOrder")
//    public Result getCompletedOrder(String token, CompletedOrder completedOrder){
//        return null;
//    }
    /**
     * 用户留言功能
     */
    @PostMapping("comment")
    public Result addComment(@RequestBody Comment comment, @RequestHeader String token){
        return  null;
    }
    @GetMapping("comment")
    public Result getComment(@RequestHeader String token){
        return null;
    }
    @DeleteMapping("comment")
    public Result deleteComment(@RequestHeader String token, @Param("commentID") Integer commentID){
        return null;
    }


}