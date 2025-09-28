package com.marketplace.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.tpo.demo.entity.*;
import com.marketplace.tpo.demo.repository.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired private CartRepository carts;
    @Autowired private ProductRepository products;
    @Autowired private OrderRepository orders;

    @Override
    @Transactional
    public Order checkout(User user) {
        Cart cart = carts.findByUser(user).orElseThrow();
        if (cart.getItems().isEmpty()) throw new RuntimeException("Carrito vacío");

        // Validar stock
        for (CartItem i : cart.getItems()) {
            Product p = products.findById(i.getProduct().getId()).orElseThrow();
            if (p.getStock() < i.getQuantity()) {
                throw new RuntimeException("Sin stock para: " + p.getName());
            }
        }

        // Crear orden y descontar stock
        Order order = new Order();
        order.setUser(user);
        double total = 0.0;
        orders.save(order);

        for (CartItem i : cart.getItems()) {
            Product p = products.findById(i.getProduct().getId()).orElseThrow();
            p.setStock(p.getStock() - i.getQuantity());
            products.save(p);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(p);
            oi.setQuantity(i.getQuantity());
            oi.setUnitPriceSnapshot(i.getUnitPriceSnapshot());
            oi.setDiscountPctSnapshot(i.getDiscountPctSnapshot());

            order.getItems().add(oi);

            total += i.getQuantity() * i.getUnitPriceSnapshot() * (1 - i.getDiscountPctSnapshot() / 100.0);
        }

        order.setTotal(total);
        orders.save(order);

        // Vaciar carrito
        cart.getItems().clear();
        carts.save(cart);

        return order;
    }
}
