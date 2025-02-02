package com.nurseit.orderproject.entity;

import com.nurseit.orderproject.enumuration.OrderStatus;
import com.nurseit.orderproject.enumuration.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name = "products")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @PrePersist
    protected void onCreate() {
        this.status = ProductStatus.CREATED;
    }
}
