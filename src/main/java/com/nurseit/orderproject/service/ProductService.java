package com.nurseit.orderproject.service;

import com.nurseit.orderproject.dto.ProductDto;
import com.nurseit.orderproject.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Получить список всех продуктов.
     *
     * @return список продуктов.
     */
    List<Product> findAll();

    /**
     * Найти продукт по его идентификатору.
     *
     * @param id идентификатор продукта.
     * @return объект Optional с продуктом, если найден, или пустой Optional.
     */
    Optional<Product> findById(Long id);

    /**
     * Создать новый продукт.
     *
     * @param product объект продукта для создания.
     * @return созданный продукт.
     */
    Product create(ProductDto product);

    /**
     * Обновить существующий продукт по его идентификатору.
     *
     * @param id             идентификатор обновляемого продукта.
     * @param updatedProduct данные для обновления.
     * @return обновленный продукт.
     */
    Product update(Long id, ProductDto updatedProduct);

    /**
     * Удалить продукт по его идентификатору.
     *
     * @param id идентификатор удаляемого продукта.
     */
    void deleteById(Long id);
}

