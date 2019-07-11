package com.example.bootopen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.Vehicle;

import javax.management.Query;
import java.util.List;

public interface IVehicleService {
    List<Vehicle> findAllVehicle(QueryWrapper<Vehicle> vehicleWrapper);
    Vehicle getVehicleById(Vehicle vehicle);
    List<Vehicle> getVehiclesByBrand(QueryWrapper<Vehicle> vehicleQueryWrapper);
    List<Vehicle> getVehicleByPrice(QueryWrapper<Vehicle> vehicleQueryWrapper);
}
