package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Order;
import com.chuvannam.applogin.repository.OrderRepository;
import com.chuvannam.applogin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found: " + id));
    }

    @Override
    public List<Order> findByCustomerId(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findByOrderStatusId(UUID orderStatusId) {
        return orderRepository.findByOrderStatusId(orderStatusId);
    }

    @Override
    public List<Order> findByCouponId(UUID couponId) {
        return orderRepository.findByCouponId(couponId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(String id, Order order) {

        Order existing = findById(id);

        existing.setCoupon(order.getCoupon());
        existing.setCustomer(order.getCustomer());
        existing.setOrderStatus(order.getOrderStatus());

        existing.setOrderApprovedAt(
                order.getOrderApprovedAt()
        );

        existing.setOrderDeliveredCarrierDate(
                order.getOrderDeliveredCarrierDate()
        );

        existing.setOrderDeliveredCustomerDate(
                order.getOrderDeliveredCustomerDate()
        );

        existing.setUpdatedBy(
                order.getUpdatedBy()
        );

        return orderRepository.save(existing);
    }

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }
}