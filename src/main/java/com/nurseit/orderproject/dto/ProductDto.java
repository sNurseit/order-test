package com.nurseit.orderproject.dto;

import com.nurseit.orderproject.enumuration.ProductStatus;
import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private ProductStatus status;
    private double price;
    private int quantity;
}
