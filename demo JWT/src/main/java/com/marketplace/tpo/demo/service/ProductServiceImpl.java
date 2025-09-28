package com.marketplace.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.marketplace.tpo.demo.entity.Product;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public Page<Product> search(String q, Double minPrice, Double maxPrice, Long categoryId, PageRequest pr) {
        return repo.search(q, minPrice, maxPrice, categoryId, pr);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Product create(Product p, User owner) {
        p.setOwner(owner);
        return repo.save(p);
    }

    @Override
    public Product update(Product p) {
        return repo.save(p);
    }

    @Override
    public void delete(Long id, User owner) {
        // (Opcional) validar owner==p.owner o rol ADMIN/SELLER
        repo.deleteById(id);
    }
}

