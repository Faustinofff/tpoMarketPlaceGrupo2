package com.marketplace.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
