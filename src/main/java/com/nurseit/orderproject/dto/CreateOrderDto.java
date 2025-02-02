package com.nurseit.orderproject.dto;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    private Long userId;
    private HashMap<Long, Integer> products; // айди продукта и их нужное количество
}
