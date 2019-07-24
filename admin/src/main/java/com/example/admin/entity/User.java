package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId("user_id")
    private Integer userId;
    @TableField("user_name")
    private String userName;
    @TableField("user_password")
    private String password;
}
