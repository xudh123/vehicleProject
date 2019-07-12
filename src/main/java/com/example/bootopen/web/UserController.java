package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.Consts.UserConsts;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IBrandService;
import com.example.bootopen.service.IUserService;
import com.example.bootopen.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IBrandService brandService;

    @PostMapping("/login")
    public String login(User user, Model model){
        /*获取用户数据*/
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);

        /*获取车辆和品牌数据*/
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        List<Vehicle> vehicleList = vehicleService.getHotVehicles();
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("brandList", brandList);

        User user1 = new User();
        if(!userService.selectUser(userQueryWrapper).isEmpty()){
            user1 = userService.selectUser(userQueryWrapper).get(0);
            redisService.set("user", user1);      //将获取到的user放入redis中
            model.addAttribute("user", user1);    //将用户数据传输到前端
            UserConsts.userLogined = 1;          //设置用户登录标志为1，表示已登录
            model.addAttribute("is_login", UserConsts.userLogined);
            return "index";
        }
        return "login_failed";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(User user){
        userService.saveUser(user);
        return "注册成功";
    }



}
