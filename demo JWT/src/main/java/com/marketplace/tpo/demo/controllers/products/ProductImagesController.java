package com.marketplace.tpo.demo.controllers.products;

import com.marketplace.tpo.demo.entity.Product;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.repository.ProductRepository;
import com.marketplace.tpo.demo.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductImagesController {

  private final ProductRepository productRepo;
  private final FileStorageService storage;

  public ProductImagesController(ProductRepository productRepo, FileStorageService storage) {
    this.productRepo = productRepo;
    this.storage = storage;
  }

  @PostMapping(value="/{id}/images", consumes = "multipart/form-data")
  public ResponseEntity<Product> uploadImages(@AuthenticationPrincipal User user,
                                              @PathVariable Long id,
                                              @RequestParam("files") MultipartFile[] files) {
    Product p = productRepo.findById(id).orElseThrow();

    // (opcional) validar que user tenga permisos (SELLER/ADMIN) o sea dueño, si tenés owner en Product

    List<String> urls = new ArrayList<>();
    if (p.getImageUrls() != null && !p.getImageUrls().isBlank()) {
      urls.addAll(Arrays.asList(p.getImageUrls().split(",")));
    }
    for (MultipartFile f : files) {
      String url = storage.saveImage(f);
      urls.add(url);
    }
    p.setImageUrls(String.join(",", urls));
    return ResponseEntity.ok(productRepo.save(p));
  }

  @DeleteMapping("/{id}/images")
  public ResponseEntity<Product> removeImage(@AuthenticationPrincipal User user,
                                             @PathVariable Long id,
                                             @RequestParam String url) {
    Product p = productRepo.findById(id).orElseThrow();

    if (p.getImageUrls() != null && !p.getImageUrls().isBlank()) {
      List<String> list = new ArrayList<>(Arrays.asList(p.getImageUrls().split(",")));
      list.removeIf(u -> u.equals(url));
      p.setImageUrls(String.join(",", list));
    }
    return ResponseEntity.ok(productRepo.save(p));
  }
}
