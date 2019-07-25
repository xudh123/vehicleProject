package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.Brand;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IAdminService;
import com.example.admin.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IBrandService brandService;

    public Admin getAdmin(){
        Admin admin = redisService.get("admin", Admin.class);
        if (admin == null){
            return new Admin();
        }
        return admin;
    }

    @RequestMapping("/admin_manage/brand_manage")
    public String toBrandManage(Model model){

        List<Brand> brandList = brandService.getBrandList();
        model.addAttribute("brandList", brandList);

        return "/admin_manage/brand_manage";
    }

    @PostMapping("addBrand.do")
    public String addBrand(Brand brand, Model model){
        brandService.addBrand(brand);

        List<Brand> brandList = brandService.getBrandList();

        model.addAttribute("brandList", brandList);
        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/brand_manage");

        return "adminIndex";
    }

    @PostMapping("delBrand.do")
    public String delBrand(int brandId, Model model){
        brandService.delBrand(brandId);

        List<Brand> brandList = brandService.getBrandList();

        model.addAttribute("brandList", brandList);
        model.addAttribute("admin", getAdmin());
        model.addAttribute("replace", "/admin_manage/brand_manage");

        return "adminIndex";
    }
}
