package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.User;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IAdminService;
import com.example.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisService redisService;

    public Admin getAdmin(){
        Admin admin = redisService.get("admin", Admin.class);
        if (admin == null){
            return new Admin();
        }
        return admin;
    }

    @PostMapping("addUser.do")
    public String addUser(User user, Model model, HttpSession session){
        List<User> userList = userService.getUserList();
        List<User> userList1 = userList.stream().filter(user1 -> user1.getUserId() == user.getUserId()).collect(Collectors.toList());
        if (userList1.isEmpty()){
            userService.addUser(user);
            session.setAttribute("message", "添加成功");
        }else {
            System.out.println("添加失败");
            session.setAttribute("message", "用户编号已存在，添加失败");
        }

        userList = userService.getUserList();

        model.addAttribute("userList", userList);
        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/user_manage");

        return "adminIndex";
    }

    @PostMapping("delUser.do")
    public String delUser(int userId, Model model){
        userService.delUser(userId);

        List<User> userList = userService.getUserList();

        model.addAttribute("userList", userList);
        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/user_manage");

        return "adminIndex";
    }

}
