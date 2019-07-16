package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.VehicleMapper;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Vehicle> getHotVehicles() {
        List<Vehicle> vehicleList = redisService.getList("vehicleHotList", Vehicle.class);
        if (vehicleList == null){                               //检查redis是否有需要的车辆数据
            vehicleList = vehicleMapper.getHotVehicles();     //redis中没有该数据则从数据库获取
            redisService.addList("vehicleHotList", vehicleList);       //将热门车辆的数据存放到redis中
        }
        return vehicleList;
    }

    @Override
    public List<Vehicle> findAllVehicle(QueryWrapper<Vehicle> vehicleWrapper) {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList = vehicleMapper.selectList(vehicleWrapper);
        return vehicleList;
    }

    @Override
    public Vehicle getVehicleById(Vehicle vehicle) {
        Vehicle vehicle_1 = vehicleMapper.selectById(vehicle);
        return vehicle_1;
    }

    @Override
    public List<Vehicle> getVehiclesByBrand(QueryWrapper<Vehicle> vehicleQueryWrapper) {
        List<Vehicle> vehicleList = vehicleMapper.selectList(vehicleQueryWrapper);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehicleByPrice(String price) {
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
        List<Vehicle> vehicleList = vehicleMapper.selectList(vehicleQueryWrapper);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByTypeFuzzyQuery(QueryWrapper<Vehicle> vehicleQueryWrapper) {
        List<Vehicle> vehicleList = vehicleMapper.selectList(vehicleQueryWrapper);
        return vehicleList;
    }
}
