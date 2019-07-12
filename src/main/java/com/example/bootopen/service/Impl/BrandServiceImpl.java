package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.BrandMapper;
import com.example.bootopen.pojo.Brand;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Brand> getBrands() {
        List<Brand> brandList = redisService.getList("brandList", Brand.class);
        if (brandList == null){
            brandList = brandMapper.selectList(new QueryWrapper<Brand>());
            redisService.addList("brandList", brandList);
        }
        return brandList;
    }
}
