package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.OrderItem;
import com.chuvannam.applogin.repository.OrderItemRepository;
import com.chuvannam.applogin.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem findById(UUID id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("OrderItem not found: " + id));
    }

    @Override
    public List<OrderItem> findByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> findByProductId(UUID productId) {
        return orderItemRepository.findByProductId(productId);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem update(UUID id, OrderItem orderItem) {

        OrderItem existing = findById(id);

        existing.setOrder(
                orderItem.getOrder()
        );

        existing.setProduct(
                orderItem.getProduct()
        );

        existing.setPrice(
                orderItem.getPrice()
        );

        existing.setQuantity(
                orderItem.getQuantity()
        );

        return orderItemRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        orderItemRepository.deleteById(id);
    }
}