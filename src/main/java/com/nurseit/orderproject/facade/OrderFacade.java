package com.nurseit.orderproject.facade;

import com.nurseit.orderproject.dto.CreateOrderDto;
import com.nurseit.orderproject.entity.Order;
import com.nurseit.orderproject.entity.OrderProduct;
import com.nurseit.orderproject.entity.Product;
import com.nurseit.orderproject.enumuration.OrderStatus;
import com.nurseit.orderproject.service.OrderProductService;
import com.nurseit.orderproject.service.OrderService;
import com.nurseit.orderproject.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final ProductService productService;

    /**
     * Создает заказ на основе переданных данных.
     *
     * @param createOrderDto DTO с информацией о заказе, включая ID пользователя и список продуктов с количеством.
     */
    @Transactional
    public void create(CreateOrderDto createOrderDto) {
        Set<Product> productsFromDb = productService.findAllByIdsWithValidQuantity(createOrderDto.getProducts().keySet());
        List<OrderProduct> orderProductList = prepareOrderProducts(createOrderDto, productsFromDb);
        Double totalPrice = calculateTotalPrice(orderProductList, productsFromDb);

        Order order = orderService.create(
                Order.builder()
                        .status(OrderStatus.PENDING)
                        .customerId(createOrderDto.getUserId())
                        .totalPrice(totalPrice)
                        .build()
        );
        productService.saveAll(productsFromDb);
        orderProductService.createAllByOrderId(orderProductList, order.getId());
    }

    /**
     * Подготавливает список OrderProduct для сохранения в базе данных.
     *
     * @param createOrderDto DTO с информацией о заказе.
     * @param productsFromDb множество продуктов, полученных из базы данных.
     * @return список OrderProduct, содержащий информацию о продуктах в заказе.
     */
    private List<OrderProduct> prepareOrderProducts(CreateOrderDto createOrderDto, Set<Product> productsFromDb) {
        List<OrderProduct> orderProductList = new ArrayList<>(createOrderDto.getProducts().size());

        productsFromDb.forEach(product -> {
            Integer needQuantity = createOrderDto.getProducts().get(product.getId());

            if (needQuantity > product.getQuantity()) {
                throw new RuntimeException("Requested quantity exceeds available stock for product ID: " + product.getId());
            }

            product.setQuantity(product.getQuantity() - needQuantity);
            orderProductList.add(
                    OrderProduct.builder()
                            .quantity(needQuantity)
                            .productId(product.getId())
                            .build()
            );
        });
        return orderProductList;
    }

    /**
     * Рассчитывает общую стоимость заказа.
     *
     * @param orderProductList список продуктов в заказе с их количеством.
     * @param products множество продуктов, доступных в базе данных.
     * @return общая стоимость заказа.
     */
    private Double calculateTotalPrice(List<OrderProduct> orderProductList, Set<Product> products) {
        HashMap<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product,
                        (existing, replacement) -> existing,
                        HashMap::new
                ));

        return orderProductList.stream()
                .mapToDouble(orderProduct ->  productMap.get(orderProduct.getProductId()).getPrice() * orderProduct.getQuantity())
                .sum();
    }
}


