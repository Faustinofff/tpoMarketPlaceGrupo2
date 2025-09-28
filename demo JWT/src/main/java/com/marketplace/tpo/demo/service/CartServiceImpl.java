package com.marketplace.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.marketplace.tpo.demo.entity.*;
import com.marketplace.tpo.demo.repository.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired private CartRepository carts;
    @Autowired private ProductRepository products;

    @Override
    public Cart getOrCreate(User user) {
        return carts.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return carts.save(c);
        });
    }

    @Override
    @Transactional
    public Cart add(User user, Long productId, int quantity) {
        Cart c = getOrCreate(user);
        Product p = products.findById(productId).orElseThrow();

        Optional<CartItem> existing = c.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem ci = new CartItem();
            ci.setCart(c);
            ci.setProduct(p);
            ci.setQuantity(quantity);
            ci.setUnitPriceSnapshot(p.getPrice());
            ci.setDiscountPctSnapshot(p.getDiscountPct() == null ? 0 : p.getDiscountPct());
            c.getItems().add(ci);
        }
        return carts.save(c);
    }

    @Override
    @Transactional
    public Cart remove(User user, Long productId) {
        Cart c = getOrCreate(user);
        c.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        return carts.save(c);
    }

    @Override
    @Transactional
    public Cart update(User user, Long productId, int quantity) {
        Cart c = getOrCreate(user);
        for (CartItem i : c.getItems()) {
            if (i.getProduct().getId().equals(productId)) {
                i.setQuantity(quantity);
            }
        }
        return carts.save(c);
    }

    @Override
    public void clear(User user) {
        Cart c = getOrCreate(user);
        c.getItems().clear();
        carts.save(c);
    }
}

