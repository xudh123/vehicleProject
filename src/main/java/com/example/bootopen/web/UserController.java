package com.example.bootopen.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.User;
import com.example.bootopen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        User user1 = userService.selectUser(userQueryWrapper).get(0);
        System.out.println(user1.getUsername() + "   " + user1.getPassword());
        if (user.getUsername().equals(user1.getUsername()) && user.getPassword().equals(user1.getPassword())){
            return "登录成功";
        }
        return "登录失败";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(User user){
        userService.saveUser(user);
        return "注册成功";
    }



}
