package com.example.bootopen.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@TableName("brand")
public class Brand implements Serializable{

    @TableId("brand_id")
    private Integer brandId;

    @TableField("brand_name")
    private String brandName;

    @TableField("brand_image")
    private String brandImage;
}
