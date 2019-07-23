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
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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


    ThreadLocal<User> authContext = new ThreadLocal<>();
    /**
     * @return 返回一个用户数据对象
     * redis中不存在就返回一个空对象
     */
    private User getUser(HttpSession session){
        if(UserConsts.userLogined == 1){       //登录标志为1，
            /*return (User) session.getAttribute("user");*/
            return redisService.get(UserConsts.userName, User.class);
        }else {
            return (new User());
        }
    }

    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session){
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
            UserConsts.userName = user1.getUsername();
            redisService.set(UserConsts.userName, user1);      //将获取到的user放入redis中
            session.setAttribute("user", user1);
            List<Vehicle> AuctionVehicles = vehicleService.getVehiclesBySaleWay(2);             //获取拍卖车辆数据
            List<Vehicle> APriceVehicles = vehicleService.getVehiclesBySaleWay(1);       //获取一口价销售车辆数据

            model.addAttribute("AuctionVehicles", AuctionVehicles);
            model.addAttribute("APriceVehicles", APriceVehicles);
            model.addAttribute("user", user1);    //将用户数据传输到前端
            UserConsts.userLogined = 1;          //设置用户登录标志为1，表示已登录
            model.addAttribute("is_login", UserConsts.userLogined);
            return "index";
        }
        model.addAttribute("is_login", UserConsts.userLogined);
        return "login_failed";
    }

    /**
     * @param user 用户数据
     * return index
     * 注册逻辑，注册成功后自动登录
     */
    @PostMapping("/register")
    public String register(User user, Model model, HttpSession session){

        userService.saveUser(user);        //保存用户数据到数据库

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        User user1 = userService.selectUser(userQueryWrapper).get(0);
        UserConsts.userName = user1.getUsername();
        redisService.set(UserConsts.userName, user1);     //添加用户信息到redis中
        UserConsts.userLogined = 1;        //设置用户登录标志为1，表示已登录

        List<Vehicle> AuctionVehicles = vehicleService.getVehiclesBySaleWay(2);             //获取拍卖车辆数据
        List<Vehicle> APriceVehicles = vehicleService.getVehiclesBySaleWay(1);       //获取一口价销售车辆数据
        List<Vehicle> vehicleList = vehicleService.getHotVehicles();           //获得热门车辆列表
        List<Brand> brandList = brandService.getBrands();               //获得车辆品牌列表

        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("brandList", brandList);
        model.addAttribute("AuctionVehicles", AuctionVehicles);
        model.addAttribute("user", user1);
        model.addAttribute("APriceVehicles", APriceVehicles);
        model.addAttribute("is_login", UserConsts.userLogined);

        session.setAttribute("user", user);
        return "index";
    }

    @PostMapping("quitLogin")
    public String quitLogin(Model model, HttpSession session){
        UserConsts.userLogined = 0;
        redisService.delete(UserConsts.userName);   //删除用户数据缓存

        List<Vehicle> AuctionVehicles = vehicleService.getVehiclesBySaleWay(2);             //获取拍卖车辆数据
        List<Vehicle> APriceVehicles = vehicleService.getVehiclesBySaleWay(1);       //获取一口价销售车辆数据
        List<Vehicle> vehicleList = vehicleService.getHotVehicles();     //获取车辆数据
        List<Brand> brandList = brandService.getBrands();  //获取品牌数据

        User user = getUser(session);
        System.out.println(user.getUserId());
        System.out.println(((User) session.getAttribute("user")).getUsername());
        session.removeAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("brandList", brandList);
        model.addAttribute("AuctionVehicles", AuctionVehicles);
        model.addAttribute("APriceVehicles", APriceVehicles);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "index";
    }

    /**
     * @return test
     * 进入用户管理界面
     */
    @PostMapping("toUserInfo")
    public String toUserInfo(Model model, HttpSession session){
        User user = getUser(session);
        model.addAttribute("user", user);

        List<Vehicle> MyVehicleList = vehicleService.getVehiclesByOwner(user.getUserId());
        model.addAttribute("MyVehicleList", MyVehicleList);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "user_manage";
    }

    /**
     * @param user 用户
     * @return test
     * 更新用户信息
     */
    @PostMapping("updateUser")
    public String updateUser(User user, Model model, HttpSession session){
        userService.updateById(user);

        User user1 = getUser(session);
        model.addAttribute("user", user1);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "user_manage";
    }

    /**
     * @return /User_info/user_info
     * 加载用户信息管理界面
     */
    @RequestMapping("/User_info/user_info.html")
    public String getUserInfo(Model model, HttpSession session){
        User user = getUser(session);

        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "/User_info/user_info";
    }

    /**
     * @return /User_info/vehicle_user
     * 加载用户拥有的车辆
     */
    @RequestMapping("/User_info/vehicle_user.html")
    public String getUserVehicles(Model model, HttpSession session){
        User user = getUser(session);
        List<Vehicle> MyVehicleList = vehicleService.getVehiclesByOwner(user.getUserId());

        int size = MyVehicleList.size();
        while (size>0){
            if(MyVehicleList.get(size-1).getVehicleOnsale() == 0){
                MyVehicleList.get(size-1).setVehicleStatus("未出售");
                MyVehicleList.get(size-1).setVehicleOperate("出售");
            }else if (MyVehicleList.get(size-1).getVehicleOnsale() == 1){
                MyVehicleList.get(size-1).setVehicleStatus("一口价出售");
                MyVehicleList.get(size-1).setVehicleOperate("停止出售");
            }else {
                MyVehicleList.get(size-1).setVehicleStatus("拍卖出售");
                MyVehicleList.get(size-1).setVehicleOperate("停止出售");
            }
            System.out.println(size);
            size--;
        }

        model.addAttribute("MyVehicleList", MyVehicleList);
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "/User_info/vehicle_user";
    }
}
