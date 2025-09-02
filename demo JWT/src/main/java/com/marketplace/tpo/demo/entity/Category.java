package com.marketplace.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Category {

    public Category() {
    }

    public Category(String description) {
        this.description = description;
    }

    @Id // Identificador único de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) //la base de datos se encarga de generar el id
    private Long id;

    @Column
    private String description;

    @OneToOne(mappedBy = "category")
    private Product product;
}
