package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.OrderMapper;
import com.example.bootopen.pojo.Order;
import com.example.bootopen.service.IOrderService;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void saveOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public List<Order> getOrderListByBuyer(String buyer_name) {
        Order order = new Order();
        order.setBuyer(buyer_name);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>(order);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        return orderList;
    }

    @Override
    public List<Order> getOrderListBySeller(String seller_name) {
        Order order = new Order();
        order.setSeller(seller_name);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>(order);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        return orderList;
    }

}
