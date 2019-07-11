package com.example.bootopen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootopen.pojo.Vehicle;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VehicleMapper extends BaseMapper<Vehicle> {

    @Select("select * from vehicle order by vehicle_id limit 4")
    List<Vehicle> getHotVehicles();
}
