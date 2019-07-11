package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.service.IUserService;
import com.example.bootopen.service.IVehicleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IVehicleService vehicleService;

    @RequestMapping("index")
    public String index(Model model){
        QueryWrapper<Vehicle> vehicleWrapper = new QueryWrapper<>();
        List<Vehicle> vehicleList = vehicleService.findAllVehicle(vehicleWrapper);
        model.addAttribute("vehicleList", vehicleList);
        return "index";
    }


}
