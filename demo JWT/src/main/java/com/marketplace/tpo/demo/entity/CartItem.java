package com.marketplace.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    /** Snapshot del precio al momento de agregar al carrito */
    @Column(nullable = false)
    private Double unitPriceSnapshot;

    /** Snapshot del % descuento al momento de agregar al carrito */
    @Column(nullable = false)
    private Integer discountPctSnapshot;
}

