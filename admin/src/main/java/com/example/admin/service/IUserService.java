package com.example.admin.service;

import com.example.admin.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUserList();
    void addUser(User user);
    void delUser(int userId);
}
