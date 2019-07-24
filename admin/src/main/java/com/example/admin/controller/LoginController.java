package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.User;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("login")
    public String toLogin(Model model){
        model.addAttribute("message", "");
        return "login";
    }

    @PostMapping("login.do")
    public String login(User user, Model model){

        Admin admin = adminService.getAdminByName(user.getUserName());
        if (admin == null){
            model.addAttribute("message", "用户名不存在!");
            System.out.println("用户名不存在!");
            return "login";
        }else if (!user.getPassword().equals(admin.getAdminPassword())){
            model.addAttribute("message", "密码错误!");
            System.out.println("密码错误!");
            return "login";
        }
        redisService.set("admin", user);
        return "adminIndex";
    }
}
