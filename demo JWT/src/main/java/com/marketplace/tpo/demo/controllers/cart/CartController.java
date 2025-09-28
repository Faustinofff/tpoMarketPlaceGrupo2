package com.marketplace.tpo.demo.controllers.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.Order;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.service.CartService;
import com.marketplace.tpo.demo.service.CheckoutService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private CheckoutService checkoutService;

    @GetMapping
    public Cart get(@AuthenticationPrincipal User user) {
        return cartService.getOrCreate(user);
    }

    @PostMapping("/add/{productId}")
    public Cart add(@AuthenticationPrincipal User user,
                    @PathVariable Long productId,
                    @RequestParam(defaultValue = "1") int qty) {
        return cartService.add(user, productId, qty);
    }

    @PostMapping("/update/{productId}")
    public Cart update(@AuthenticationPrincipal User user,
                       @PathVariable Long productId,
                       @RequestParam int qty) {
        return cartService.update(user, productId, qty);
    }

    @PostMapping("/remove/{productId}")
    public Cart remove(@AuthenticationPrincipal User user,
                       @PathVariable Long productId) {
        return cartService.remove(user, productId);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(checkoutService.checkout(user));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}

