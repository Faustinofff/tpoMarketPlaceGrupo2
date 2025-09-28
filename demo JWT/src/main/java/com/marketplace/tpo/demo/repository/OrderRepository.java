package com.marketplace.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.tpo.demo.entity.Order;
import com.marketplace.tpo.demo.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
