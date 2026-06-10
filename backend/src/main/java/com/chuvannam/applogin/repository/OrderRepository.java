package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository
        extends JpaRepository<Order, String> {

    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByOrderStatusId(UUID orderStatusId);

    List<Order> findByCouponId(UUID couponId);
}