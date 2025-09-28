package com.marketplace.tpo.demo.controllers.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.marketplace.tpo.demo.entity.*;
import com.marketplace.tpo.demo.repository.CategoryRepository;
import com.marketplace.tpo.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService products;
    @Autowired private CategoryRepository categories;

    @GetMapping
    public Page<Product> list(@RequestParam(required = false) String q,
                              @RequestParam(required = false) Double minPrice,
                              @RequestParam(required = false) Double maxPrice,
                              @RequestParam(required = false) Long categoryId,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "12") int size) {
        return products.search(q, minPrice, maxPrice, categoryId, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return products.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@AuthenticationPrincipal User user, @RequestBody ProductRequest req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setDiscountPct(req.getDiscountPct());
        p.setImageUrls(req.getImageUrls());

        if (req.getCategoryId() != null) {
            Category c = categories.findById(req.getCategoryId()).orElse(null);
            p.setCategory(c);
        }

        Product saved = products.create(p, user);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@AuthenticationPrincipal User user,
                                          @PathVariable Long id,
                                          @RequestBody ProductRequest req) {
        return products.getById(id).map(p -> {
            if (req.getName() != null) p.setName(req.getName());
            if (req.getDescription() != null) p.setDescription(req.getDescription());
            if (req.getPrice() != null) p.setPrice(req.getPrice());
            if (req.getStock() != null) p.setStock(req.getStock());
            if (req.getDiscountPct() != null) p.setDiscountPct(req.getDiscountPct());
            if (req.getImageUrls() != null) p.setImageUrls(req.getImageUrls());
            if (req.getCategoryId() != null) {
                categories.findById(req.getCategoryId()).ifPresent(p::setCategory);
            }
            return ResponseEntity.ok(products.update(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        products.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
