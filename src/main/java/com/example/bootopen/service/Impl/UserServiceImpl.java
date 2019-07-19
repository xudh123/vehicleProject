package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.UserMapper;
import com.example.bootopen.pojo.User;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public List<User> selectUser(QueryWrapper<User> userQueryWrapper){
        return userMapper.selectList(userQueryWrapper);
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);

        /*更新用户数据到redis中*/
        redisService.set("user", user);
    }
    /**
     * @param userId 用户id
     * @return user 查询出来的用户信息
     * 根据用户id查找相应的用户
     */
    @Override
    public User selectUserById(int userId) {
        User user = new User();
        user.setUserId(userId);
        return userMapper.selectById(user);
    }
}
