package com.marketplace.tpo.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marketplace.tpo.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

    @Query("""
           SELECT p FROM Product p
           WHERE (:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) 
                           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')))
             AND (:minPrice IS NULL OR p.price >= :minPrice)
             AND (:maxPrice IS NULL OR p.price <= :maxPrice)
             AND (:categoryId IS NULL OR p.category.id = :categoryId)
           """)
    Page<Product> search(String q, Double minPrice, Double maxPrice, Long categoryId, Pageable pageable);

    List<Product> findByOwner_Id(Long ownerId);
}
