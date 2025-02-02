package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.entity.Order;
import com.nurseit.orderproject.enumuration.OrderStatus;
import com.nurseit.orderproject.repository.OrderRepository;
import com.nurseit.orderproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Order> filter(OrderStatus status, Integer minPrice, Integer maxPrice) {
        return List.of();
    }

}
