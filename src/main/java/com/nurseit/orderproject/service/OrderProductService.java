package com.nurseit.orderproject.service;

import com.nurseit.orderproject.entity.OrderProduct;

import java.util.List;

public interface OrderProductService {
    void createAllByOrderId(List<OrderProduct> orderProduct, Long orderId);
}
