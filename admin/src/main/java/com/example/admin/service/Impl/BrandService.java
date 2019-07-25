package com.example.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.entity.Brand;
import com.example.admin.mapper.BrandMapper;
import com.example.admin.redis.RedisService;
import com.example.admin.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Brand> getBrandList() {

        List<Brand> brandList = redisService.getList("brandList", Brand.class);
        if (brandList == null){
            QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
            brandList = brandMapper.selectList(brandQueryWrapper);
            redisService.addList("brandList", brandList);
        }

        return brandList;
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insert(brand);

        redisService.delete("brandList");
    }

    @Override
    public void delBrand(int brandId) {
        Brand brand = new Brand().setBrandId(brandId);

        brandMapper.deleteById(brand);
        redisService.delete("brandList");
    }
}
