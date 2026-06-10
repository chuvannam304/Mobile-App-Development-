package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.OrderStatus;
import com.chuvannam.applogin.repository.OrderStatusRepository;
import com.chuvannam.applogin.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus findById(UUID id) {
        return orderStatusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("OrderStatus not found: " + id));
    }

    @Override
    public OrderStatus findByStatusName(String statusName) {
        return orderStatusRepository.findByStatusName(statusName)
                .orElseThrow(() ->
                        new RuntimeException("OrderStatus not found: " + statusName));
    }

    @Override
    public List<OrderStatus> findByPrivacy(String privacy) {
        return orderStatusRepository.findByPrivacy(privacy);
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public OrderStatus update(UUID id, OrderStatus orderStatus) {

        OrderStatus existing = findById(id);

        existing.setStatusName(orderStatus.getStatusName());
        existing.setColor(orderStatus.getColor());
        existing.setPrivacy(orderStatus.getPrivacy());

        existing.setCreatedBy(orderStatus.getCreatedBy());
        existing.setUpdatedBy(orderStatus.getUpdatedBy());

        return orderStatusRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        orderStatusRepository.deleteById(id);
    }
}