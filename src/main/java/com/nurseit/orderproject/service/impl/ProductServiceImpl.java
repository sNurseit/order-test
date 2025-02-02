package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.dto.ProductDto;
import com.nurseit.orderproject.entity.Product;
import com.nurseit.orderproject.enumuration.ProductStatus;
import com.nurseit.orderproject.repository.OrderProductRepository;
import com.nurseit.orderproject.repository.ProductRepository;
import com.nurseit.orderproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Set<Product> findAllByIdsWithValidQuantity(Set<Long> ids) {
        Set<Product> products = repository.findAllByIdIn(ids);
        if (!products.stream().map(Product::getId).collect(Collectors.toSet()).containsAll(ids)) {
            throw new RuntimeException("Some products not found in the database for the provided IDs");
        }
        return products;
    }


    public Product create(ProductDto productDto) {
        return repository.save(
                Product.builder()
                        .name(productDto.getName())
                        .price(productDto.getPrice())
                        .quantity(productDto.getQuantity())
                        .build());
    }

    public Product update(Long id, ProductDto updatedProduct) {
        return repository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            return repository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(ProductStatus.DELETED);
    }

    @Override
    public void saveAll(Set<Product> products) {
        repository.saveAll(products);
    }
}
