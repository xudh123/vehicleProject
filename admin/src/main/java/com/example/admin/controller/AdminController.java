package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.Brand;
import com.example.admin.entity.User;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IAdminService;
import com.example.admin.service.IBrandService;
import com.example.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IUserService userService;

    public Admin getAdmin(){
        Admin admin = redisService.get("admin", Admin.class);
        if (admin == null){
            return new Admin();
        }
        return admin;
    }

    @RequestMapping("/admin_manage/brand_manage")
    public String toBrandManage(Model model, HttpSession session){

        List<Brand> brandList = brandService.getBrandList();
        model.addAttribute("brandList", brandList);
        model.addAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");

        return "/admin_manage/brand_manage";
    }

    @RequestMapping("/admin_manage/user_manage")
    public String toUserManage(Model model, HttpSession session){

        List<User> userList = userService.getUserList();

        model.addAttribute("userList", userList);
        model.addAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");

        return "/admin_manage/user_manage";
    }

    @PostMapping("addBrand.do")
    public String addBrand(Brand brand, Model model, HttpSession session){
        List<Brand> brandList = brandService.getBrandList();
        List<Brand> brandList1 = brandList.stream().filter(brand1 -> brand1.getBrandId() == brand.getBrandId()).collect(Collectors.toList());
        if (brandList1.isEmpty()){
            session.setAttribute("message", "添加成功");
            brandService.addBrand(brand);
        } else {
            session.setAttribute("message", "品牌数据添加失败，编号已存在");
        }

        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/brand_manage");

        return "adminIndex";
    }

    @PostMapping("delBrand.do")
    public String delBrand(int brandId, Model model){
        brandService.delBrand(brandId);

        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/brand_manage");

        return "adminIndex";
    }
}
