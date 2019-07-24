package com.example.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.entity.Admin;
import com.example.admin.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;


    /**
     * @param adminUserName 管理员用户名
     * @return adminList.get(0)
     */
    @Override
    public Admin getAdminByName(String adminUserName) {
        Admin admin = new Admin().setAdminUserName(adminUserName);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>(admin);
        List<Admin> adminList = adminMapper.selectList(adminQueryWrapper);

        if (adminList.isEmpty()){
            return null;
        }
        return adminList.get(0);
    }
}
