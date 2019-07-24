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
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private RedisService redisService;
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
            return redisService.get(UserConsts.userName, User.class);
        }else {
            return (new User());
        }
    }

    /**
     * @param model
     * @return
     * 前端测试
     */
    @RequestMapping("test")
    public String test(Model model){
        User user = getUser();

        model.addAttribute("user", user);          //用户数据
        model.addAttribute("is_login", UserConsts.userLogined);    //是否有用户登录
        return "test";
    }

    /**
     * @param model 前后端数据传输
     * 获取前端需要的数据
     */
    @RequestMapping("index")
    public String index(Model model){

        List<Vehicle> vehicleList = vehicleService.getHotVehicles();     //获取部分车辆数据
        List<Vehicle> AuctionVehicles = vehicleService.getVehiclesBySaleWay(2);             //获取拍卖车辆数据
        List<Vehicle> APriceVehicles = vehicleService.getVehiclesBySaleWay(1);       //获取一口价销售车辆数据
        List<Brand> brandList = brandService.getBrands();  //获取品牌数据

        User user = getUser();

        model.addAttribute("user", user);          //用户数据
        model.addAttribute("vehicleList", vehicleList);  //车辆数据
        model.addAttribute("APriceVehicles", APriceVehicles);
        model.addAttribute("AuctionVehicles", AuctionVehicles);
        model.addAttribute("brandList", brandList);     //品牌数据
        model.addAttribute("is_login", UserConsts.userLogined);    //是否有用户登录
        return "index";
    }

    /**
     * @param info_vehicle 前端输入的查询条件
     * @param model 前后端数据传输
     * @return 返回符合条件的数据
     */
    @PostMapping("/search_Vehicle")
    public String searchVehicle(String info_vehicle, Model model){
        Vehicle vehicle = new Vehicle();
        /*按品牌查询，非模糊查询*/
        vehicle.setVehicleBrand(info_vehicle);
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>(vehicle);

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList = vehicleService.getVehiclesByBrand(vehicleQueryWrapper);   //按品牌查询
        User user = getUser();
        model.addAttribute("user", user);

        if (vehicleList.isEmpty()){                  //按品牌查询为空，则根据车系进行模糊查询
            vehicleQueryWrapper = new QueryWrapper<Vehicle>().like("vehicle_type", info_vehicle);
            List<Vehicle> vehicleList1 = vehicleService.getVehiclesByTypeFuzzyQuery(vehicleQueryWrapper);

            model.addAttribute("vehicleList", vehicleList1);
            return "buy_vehicle";
        }


        model.addAttribute("is_login", UserConsts.userLogined);
        model.addAttribute("vehicleList", vehicleList);

        return "buy_vehicle";
    }

}
