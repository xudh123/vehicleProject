package com.example.bootopen.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("offer")
public class Offer implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer offerId;
    @TableField("vehicle_id")
    private Integer vehicleId;
    @TableField("buyer_name")
    private String buyerName;
    @TableField("seller_name")
    private String sellerName;
    @TableField("offer_price")
    private Double offerPrice;
    @TableField("offer_status")
    private String offerStatus;

    @TableField(exist = false)
    private String vehicleBrand;
    @TableField(exist = false)
    private String vehicleType;
    @TableField(exist = false)
    private Double vehiclePrice;
    @TableField(exist = false)
    private Integer vehicleOnSale;
}
