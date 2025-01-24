package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.dto.ProductDto;
import com.nurseit.orderproject.entity.Product;
import com.nurseit.orderproject.enumuration.ProductStatus;
import com.nurseit.orderproject.repository.ProductRepository;
import com.nurseit.orderproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product create(ProductDto productDto) {
        return productRepository.save(
                Product.builder()
                        .name(productDto.getName())
                        .price(productDto.getPrice())
                        .quantity(productDto.getQuantity())
                        .build());
    }

    public Product update(Long id, ProductDto updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(ProductStatus.DELETED);
    }
}
