package com.example.bootopen.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    @TableId("user_name")
    private String username;
    @TableField("user_password")
    private String password;

    public User(){}

    public User(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }

}
