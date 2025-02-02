package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.entity.OrderProduct;
import com.nurseit.orderproject.repository.OrderProductRepository;
import com.nurseit.orderproject.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository repository;

    public void createAllByOrderId(List<OrderProduct> orderProducts, Long orderId){
        orderProducts.forEach(orderProduct -> orderProduct.setOrderId(orderId));
        repository.saveAll(orderProducts);
    }
}
