package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("brand")
public class Brand implements Serializable {
    @TableId("brand_id")
    private Integer brandId;
    @TableField("brand_name")
    private String brandName;
    @TableField("brand_image")
    private String brandImage;
}
