package com.marketplace.tpo.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import com.marketplace.tpo.demo.entity.Product;
import com.marketplace.tpo.demo.entity.User;

public interface ProductService {
    Page<Product> search(String q, Double minPrice, Double maxPrice, Long categoryId, PageRequest pr);
    Optional<Product> getById(Long id);
    Product create(Product p, User owner);
    Product update(Product p);
    void delete(Long id, User owner);
}
