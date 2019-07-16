package com.example.bootopen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.User;

import java.util.List;

public interface IUserService {
    void saveUser(User user);
    List<User> selectUser(QueryWrapper<User> userQueryWrapper);
    void updateById(User user);
}
