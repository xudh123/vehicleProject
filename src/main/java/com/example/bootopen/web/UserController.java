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
import com.sun.org.apache.xpath.internal.operations.Mod;
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

    @PostMapping("quitLogin")
    public String quitLogin(Model model){
        UserConsts.userLogined = 0;
        redisService.delete("user");   //删除用户数据缓存

        List<Vehicle> vehicleList = vehicleService.getHotVehicles();     //获取车辆数据
        List<Brand> brandList = brandService.getBrands();  //获取品牌数据

        User user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("brandList", brandList);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "index";
    }

    /**
     * @return test
     * 进入用户管理界面
     */
    @PostMapping("toUserInfo")
    public String toUserInfo(Model model){
        User user = getUser();
        model.addAttribute("user", user);

        List<Vehicle> MyVehicleList = vehicleService.getVehiclesByOwner(user.getUserId());
        model.addAttribute("MyVehicleList", MyVehicleList);
        return "user_manage";
    }

    /**
     * @param user 用户
     * @return test
     * 更新用户信息
     */
    @PostMapping("updateUser")
    public String updateUser(User user, Model model){
        userService.updateById(user);

        User user1 = getUser();
        model.addAttribute("user", user1);
        return "user_manage";
    }

    /**
     * @return /User_info/user_info
     * 加载用户信息管理界面
     */
    @RequestMapping("/User_info/user_info.html")
    public String getUserInfo(Model model){
        User user = getUser();
        model.addAttribute("user", user);

        return "/User_info/user_info";
    }

    /**
     * @return /User_info/vehicle_user
     * 加载用户拥有的车辆
     */
    @RequestMapping("/User_info/vehicle_user.html")
    public String getUserVehicles(Model model){
        User user = getUser();
        List<Vehicle> MyVehicleList = vehicleService.getVehiclesByOwner(user.getUserId());
        model.addAttribute("MyVehicleList", MyVehicleList);
        model.addAttribute("user", user);
        return "/User_info/vehicle_user";
    }
}
