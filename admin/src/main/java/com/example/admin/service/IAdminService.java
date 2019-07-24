package com.example.admin.service;

import com.example.admin.entity.Admin;

public interface IAdminService {
    Admin getAdminByName(String adminUserName);
}
