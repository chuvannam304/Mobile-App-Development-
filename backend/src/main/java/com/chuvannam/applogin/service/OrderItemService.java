package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {

    List<OrderItem> findAll();

    OrderItem findById(UUID id);

    List<OrderItem> findByOrderId(String orderId);

    List<OrderItem> findByProductId(UUID productId);

    OrderItem save(OrderItem orderItem);

    OrderItem update(UUID id, OrderItem orderItem);

    void delete(UUID id);
}