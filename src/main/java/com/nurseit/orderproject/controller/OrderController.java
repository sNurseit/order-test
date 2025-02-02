package com.nurseit.orderproject.controller;

import com.nurseit.orderproject.dto.CreateOrderDto;
import com.nurseit.orderproject.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PutMapping("/{id}")
    public void getData(@PathVariable Long id) {
        log.info("Hello World!");
    }

    @GetMapping
    public void getDataas() {
        log.info("Hello World!");
    }

    @PostMapping
    public void create(@RequestBody HashMap<Long, Integer> products) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userId != null) {
            log.info("user id is " + userId);
            orderFacade.create(CreateOrderDto.builder()
                    .products(products)
                    .userId(Long.valueOf(userId))
                    .build());
        }
    }
}
