package com.example.bootopen.service;

import com.example.bootopen.pojo.Order;

import java.util.List;

public interface IOrderService {
    void saveOrder(Order order);
    List<Order> getOrderListByBuyer(String buyer_name);
    List<Order> getOrderListBySeller(String seller_name);
}
