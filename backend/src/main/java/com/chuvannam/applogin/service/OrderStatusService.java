package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderStatusService {

    List<OrderStatus> findAll();

    OrderStatus findById(UUID id);

    OrderStatus findByStatusName(String statusName);

    List<OrderStatus> findByPrivacy(String privacy);

    OrderStatus save(OrderStatus orderStatus);

    OrderStatus update(UUID id, OrderStatus orderStatus);

    void delete(UUID id);
}