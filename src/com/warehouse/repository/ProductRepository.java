package com.warehouse.repository;

import com.warehouse.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();

    List<Product> searchProducts(String keyword);
}