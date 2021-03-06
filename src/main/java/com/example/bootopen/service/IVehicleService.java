package com.example.bootopen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.pojo.Vehicle;
import com.sun.tools.corba.se.idl.StringGen;

import javax.management.Query;
import java.util.List;

public interface IVehicleService {
    List<Vehicle> getHotVehicles();
    List<Vehicle> findAllVehicle(QueryWrapper<Vehicle> vehicleWrapper);
    Vehicle getVehicleById(Vehicle vehicle);
    List<Vehicle> getVehiclesByBrand(QueryWrapper<Vehicle> vehicleQueryWrapper);
    List<Vehicle> getVehicleByPrice(String price);
    List<Vehicle> getVehiclesByTypeFuzzyQuery(QueryWrapper<Vehicle> vehicleQueryWrapper);
    List<Vehicle> getVehiclesByOwner(int ownerId);
    List<Vehicle> getVehiclesBySaleWay(int sale);
    void updateVehicleById(Vehicle vehicle);
    void insertVehicle(Vehicle vehicle);
    List<Vehicle> getVehicle(Vehicle vehicle);
}
