package com.marketplace.tpo.demo.controllers.products;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Double price;
    private Integer stock;
    private Integer discountPct;
    private String imageUrls; // URLs separadas por coma
}
