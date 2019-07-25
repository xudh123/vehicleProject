package com.example.admin.service;

import com.example.admin.entity.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> getBrandList();
    void addBrand(Brand brand);
    void delBrand(int brandId);
}
