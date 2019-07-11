package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.Vehicle;
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

    /*点击车辆图片后跳转到该控制器*/
    @PostMapping("/forVehicle")
    public String toVehicle(String vehicleId, Model model){
        Vehicle vehicle = new Vehicle();
        System.out.println(vehicleId);
        vehicle.setVehicleId(Integer.parseInt(vehicleId));
        vehicle = vehicleService.getVehicleById(vehicle);
        System.out.println(vehicle.getVehicleId() + vehicle.getVehicleTestInfo());
        model.addAttribute("vehicle_see", vehicle);
        return "vehicle";
    }

    /*点击买车跳转到车辆销售页面*/
    @PostMapping("/getVehicles")
    public String getVehicle(Model model){
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
        List<Vehicle> vehicleList = vehicleService.findAllVehicle(vehicleQueryWrapper);
        model.addAttribute("vehicleList", vehicleList);
        return "buy_vehicle";
    }

    /*按品牌进行车辆筛选*/
    @PostMapping("/getVehiclesByBrand")
    public String getVehiclesByBrand(String brand, Model model){
        Vehicle vehicle = new Vehicle();
        System.out.println(brand);
        vehicle.setVehicleBrand(brand);
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>(vehicle);
        List<Vehicle> vehicleList = vehicleService.getVehiclesByBrand(vehicleQueryWrapper);
        model.addAttribute("vehicleList", vehicleList);
        return "buy_vehicle";
    }

    /*按价格筛选车辆*/
    @PostMapping("/getVehiclesByPrice")
    public String getVehiclesByPrice(String price, Model model){
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
        System.out.println(price);
        switch (price){
            case "price1":
                vehicleQueryWrapper = new QueryWrapper<Vehicle>().lt("vehicle_price", 15);
                System.out.println(price);
                break;
            case "price2":
                vehicleQueryWrapper = new QueryWrapper<Vehicle>().between("vehicle_price", 15, 30);
                break;
            case  "price3":
                vehicleQueryWrapper = new QueryWrapper<Vehicle>().gt("vehicle_price", 30);
                break;
            default:
                break;
        }
        List<Vehicle> vehicleList = vehicleService.getVehicleByPrice(vehicleQueryWrapper);
        model.addAttribute("vehicleList", vehicleList);
        return "buy_vehicle";
    }
}
