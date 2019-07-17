package com.example.bootopen.service.Impl;

import com.example.bootopen.mapper.OrderMapper;
import com.example.bootopen.pojo.Order;
import com.example.bootopen.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void saveOrder(Order order) {
        orderMapper.insert(order);
    }
}
