package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.service.IBrandService;
import com.example.bootopen.service.IUserService;
import com.example.bootopen.service.IVehicleService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IBrandService brandService;

    @RequestMapping("index")
    public String index(Model model){
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        List<Vehicle> vehicleList = vehicleService.getHotVehicles();
        List<Brand> brandList = brandService.getBrands(brandQueryWrapper);
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("brandList", brandList);
        return "index";
    }

    @PostMapping("/search_Vehicle")
    public String searchVehicle(String info_vehicle, Model model){
        Vehicle vehicle = new Vehicle();
        /*按品牌查询，非模糊查询*/
        vehicle.setVehicleBrand(info_vehicle);
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>(vehicle);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList = vehicleService.getVehiclesByBrand(vehicleQueryWrapper);

        if (vehicleList.isEmpty()){
            System.out.println(1234);
            /*按车系进行模糊查询*/
            vehicleQueryWrapper = new QueryWrapper<Vehicle>().like("vehicle_type", info_vehicle);
            List<Vehicle> vehicleList1 = vehicleService.getVehiclesByTypeFuzzyQuery(vehicleQueryWrapper);
            model.addAttribute("vehicleList", vehicleList1);
            return "buy_vehicle";
        }

        model.addAttribute("vehicleList", vehicleList);

        return "buy_vehicle";
    }

}
