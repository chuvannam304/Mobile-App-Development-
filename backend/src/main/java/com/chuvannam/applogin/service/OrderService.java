package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> findAll();

    Order findById(String id);

    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByOrderStatusId(UUID orderStatusId);

    List<Order> findByCouponId(UUID couponId);

    Order save(Order order);

    Order update(String id, Order order);

    void delete(String id);
}