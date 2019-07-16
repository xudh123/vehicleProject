package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.Consts.UserConsts;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IBrandService;
import com.example.bootopen.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private RedisService redisService;

    private User getUser(){
        if(UserConsts.userLogined == 1){       //登录标志为1，
            return redisService.get("user", User.class);
        }else {
            return (new User());
        }
    }

    /*点击车辆图片后跳转到该控制器*/
    @PostMapping("/forVehicle")
    public String toVehicle(String vehicleId, Model model){
        Vehicle vehicle = new Vehicle();
        System.out.println(vehicleId);
        vehicle.setVehicleId(Integer.parseInt(vehicleId));
        vehicle = vehicleService.getVehicleById(vehicle);
        System.out.println(vehicle.getVehicleId() + vehicle.getVehicleTestInfo());

        User user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("vehicle_see", vehicle);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "vehicle";
    }

    /*点击买车跳转到车辆销售页面*/
    @PostMapping("/getVehicles")
    public String getVehicle(Model model){
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
        List<Vehicle> vehicleList = vehicleService.findAllVehicle(vehicleQueryWrapper);

        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("choice", "0");        //用户选项
        User user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "buy_vehicle";
    }

    /*按品牌进行车辆筛选*/
    @PostMapping("/getVehiclesByBrand")
    public String getVehiclesByBrand(Vehicle vehicle, Model model){
        System.out.println(vehicle.getVehicleBrand());
        List<Brand> brandList = brandService.getBrands();

        String chioce = "";             //寻找用户点击的品牌类型，传递到前端显示特效
        for (Brand brand : brandList){
            if (vehicle.getVehicleBrand().equals(brand.getBrandName())){
                chioce = "brand"+brand.getBrandId().toString();
            }
        }

        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>(vehicle);
        List<Vehicle> vehicleList = vehicleService.getVehiclesByBrand(vehicleQueryWrapper);      //获得需要的车辆List

        User user = getUser();
        model.addAttribute("choice", chioce);
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        model.addAttribute("vehicleList", vehicleList);
        return "buy_vehicle";
    }

    /*按价格筛选车辆*/
    @PostMapping("/getVehiclesByPrice")
    public String getVehiclesByPrice(String price, Model model){
        List<Vehicle> vehicleList = vehicleService.getVehicleByPrice(price);

        User user = getUser();
        model.addAttribute("choice", price);             //将用户选择的价格类型传递到前端
        model.addAttribute("user", user);
        model.addAttribute("is_login", UserConsts.userLogined);
        model.addAttribute("vehicleList", vehicleList);
        return "buy_vehicle";
    }
}
