package com.nurseit.orderproject.service;

import com.nurseit.orderproject.entity.Order;
import com.nurseit.orderproject.enumuration.OrderStatus;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    void deleteById(Long id);
    List<Order> filter(OrderStatus status, Integer minPrice, Integer maxPrice);
}
