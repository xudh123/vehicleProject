package com.example.bootopen.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("vehicle")
public class Vehicle implements Serializable{
    public Vehicle(){}

    @TableId(type = IdType.AUTO)
    private Integer vehicleId;

    @TableField("vehicle_brand")
    private String vehicleBrand;

    @TableField("vehicle_type")
    private String vehicleType;

    @TableField("vehicle_price")
    private Double vehiclePrice;

    @TableField("vehicle_usedtime")
    private String vehicleUsedTime;

    @TableField("vehicle_mileage")
    private String vehicleMileage;

    @TableField("vehicle_testinfo")
    private String vehicleTestInfo;

    @TableField("vehicle_image")
    private String vehicleImage;

    @TableField("vehicle_owner")
    private Integer vehicleOwner;

    @TableField("vehicle_onsale")
    private Integer vehicleOnsale;

    @TableField(exist = false)
    private String vehicleOwnerName;    //车主用户名

    @TableField(exist = false)
    private String vehicleStatus;      //车辆状态

    @TableField(exist = false)  //车辆可执行操作
    private String vehicleOperate;
}
