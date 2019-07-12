package com.example.bootopen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.pojo.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> getBrands();
}
