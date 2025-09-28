package com.marketplace.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    /** URLs separadas por coma. */
    @Column(length = 4000)
    private String imageUrls;

    /** Descuento en porcentaje (0-100). */
    @Column
    private Integer discountPct = 0;

    /** Vendedor creador del producto */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}

