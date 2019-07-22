package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.Consts.UserConsts;
import com.example.bootopen.pojo.Order;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IOrderService;
import com.example.bootopen.service.IUserService;
import com.example.bootopen.service.IVehicleService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.omg.IOP.IOR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IOrderService orderService;

    /**
     * @return 返回一个用户数据对象
     * redis中不存在就返回一个空对象
     */
    private User getUser(){
        if(UserConsts.userLogined == 1){       //登录标志为1，
            return redisService.get("user", User.class);
        }else {
            return (new User());
        }
    }

    /**
     * @param  seller_id 卖家id
     * @param order 订单
     * @return test 测试页面
     * 用户提交订单  交易方式一口价
     */
    @PostMapping("finish_order")
    public String finishOrder(String seller_id, Order order, Model model){
        System.out.println(seller_id);
        System.out.println(order);

        User user1 = new User();
        user1.setUserId(Integer.parseInt(seller_id));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user1);
        String seller = userService.selectUser(userQueryWrapper).get(0).getUsername();     //获取卖家用户名

        order.setBuyer(getUser().getUsername()).setSeller(seller);      //设置订单中的卖家名和买家名

        orderService.saveOrder(order); //保存订单

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(order.getVehicleId());
        vehicle = vehicleService.getVehicleById(vehicle); //获取交易车辆

        vehicle.setVehicleOwner(getUser().getUserId());        //更换车辆所有者
        vehicle.setVehicleOnsale(0);                 //设置车辆已交易，不在出售状态
        vehicleService.updateVehicleById(vehicle);

        System.out.println(vehicle);

        model.addAttribute("vehicle_see", vehicle);
        model.addAttribute("user", getUser());
        model.addAttribute("is_login", UserConsts.userLogined);

        return "vehicle";
    }

    /**
     * @param model
     * @return
     * 加载用户买车订单界面
     */
    @RequestMapping("/User_info/vehicle_buyed.html")
    public String getUserOrders(Model model){
        User user = getUser();

        List<Order> orderList = orderService.getOrderListByBuyer(user.getUsername());

        model.addAttribute("orderList", orderList);
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "/User_info/vehicle_buyed";
    }


    /**
     * @param model
     * @return
     * 加载用户卖车订单界面
     */
    @RequestMapping("/User_info/vehicle_selled.html")
    public String getSellerOrders(Model model){
        User user = getUser();
        List<Order> orderList = orderService.getOrderListBySeller(user.getUsername());

        model.addAttribute("orderList", orderList);
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "/User_info/vehicle_selled";
    }
}
