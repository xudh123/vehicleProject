package com.example.bootopen.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("tb_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer orderId;
    @TableField("buyer")
    private String buyer;
    @TableField("seller")
    private String seller;
    @TableField("vehicle_id")
    private Integer vehicleId;
    @TableField("address")
    private String address;
    @TableField("real_name")
    private String realName;
    @TableField("phone_number")
    private String phoneNumber;
    @TableField("order_price")
    private Double orderPrice;
}
