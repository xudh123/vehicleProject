package com.example.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<User> getUserList() {
        List<User> userList = redisService.getList("userList", User.class);
        if (userList == null){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(new User());
            userList = userMapper.selectList(userQueryWrapper);
            redisService.set("userList", userList);
        }
        return userList;
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);

        redisService.delete("userList");
    }

    @Override
    public void delUser(int userId) {
        User user = new User().setUserId(userId);
        userMapper.deleteById(user);

        redisService.delete("userList");
    }


}
