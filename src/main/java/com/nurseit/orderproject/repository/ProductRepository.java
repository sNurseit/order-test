package com.nurseit.orderproject.repository;

import com.nurseit.orderproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findAllByIdIn(Set<Long> ids);
}
