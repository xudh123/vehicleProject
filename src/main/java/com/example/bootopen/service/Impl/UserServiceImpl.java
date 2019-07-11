package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.UserMapper;
import com.example.bootopen.pojo.User;
import com.example.bootopen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public List<User> selectUser(QueryWrapper<User> userQueryWrapper){
        return userMapper.selectList(userQueryWrapper);
    }
}
