package com.example.bootopen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootopen.pojo.Vehicle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VehicleMapper extends BaseMapper<Vehicle> {

    @Select("select * from vehicle order by vehicle_id limit 4")
    List<Vehicle> getHotVehicles();
    @Select("select * from vehicle where vehicle_onsale=#{sale_way} order by vehicle_id limit 3")
    List<Vehicle> getVehiclesBySaleWay(@Param("sale_way") int saleWay);
}
