package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.VehicleMapper;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> getHotVehicles() {
        return vehicleMapper.getHotVehicles();
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
    public List<Vehicle> getVehicleByPrice(QueryWrapper<Vehicle> vehicleQueryWrapper) {
        List<Vehicle> vehicleList = vehicleMapper.selectList(vehicleQueryWrapper);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByTypeFuzzyQuery(QueryWrapper<Vehicle> vehicleQueryWrapper) {
        List<Vehicle> vehicleList = vehicleMapper.selectList(vehicleQueryWrapper);
        return vehicleList;
    }
}
