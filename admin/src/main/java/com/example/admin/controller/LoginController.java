package com.example.admin.controller;

import com.example.admin.consts.AdminConsts;
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

    /**
     * @param user 用户提交的数据
     * @return uri 跳转路径
     */
    @PostMapping("login.do")
    public String login(Admin user, Model model){

        Admin admin = adminService.getAdminByName(user.getAdminUserName());
        if (admin == null){
            model.addAttribute("message", "用户名不存在!");
            System.out.println("用户名不存在!");
            return "login";
        }else if (!user.getAdminPassword().equals(admin.getAdminPassword())){
            model.addAttribute("message", "密码错误!");
            System.out.println("密码错误!");
            return "login";
        }
        AdminConsts.adminName = admin.getAdminUserName();
        redisService.set(AdminConsts.adminName, admin);
        model.addAttribute("admin", admin);
        model.addAttribute("replace", "/admin_manage/user_manage");
        return "adminIndex";
    }

    @PostMapping("quitLogin")
    public String quitLogin(Model model){
        redisService.delete(AdminConsts.adminName);
        model.addAttribute("message", "");
        return "login";
    }
}
