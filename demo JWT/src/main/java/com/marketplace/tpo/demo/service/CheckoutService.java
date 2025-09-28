package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.Order;
import com.marketplace.tpo.demo.entity.User;

public interface CheckoutService {
    Order checkout(User user);
}
