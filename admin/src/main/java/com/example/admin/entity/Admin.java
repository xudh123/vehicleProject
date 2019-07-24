package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@TableName("admin")
@Accessors(chain = true)
@Data
public class Admin implements Serializable {
    @TableId("admin_id")
    private Integer adminId;
    @TableField("admin_username")
    private String adminUserName;
    @TableField("admin_password")
    private String adminPassword;
}
