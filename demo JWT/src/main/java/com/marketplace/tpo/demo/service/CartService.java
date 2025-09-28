package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.User;

public interface CartService {
    Cart getOrCreate(User user);
    Cart add(User user, Long productId, int quantity);
    Cart remove(User user, Long productId);
    Cart update(User user, Long productId, int quantity);
    void clear(User user);
}
