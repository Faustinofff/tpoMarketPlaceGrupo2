package com.marketplace.tpo.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

  @Value("${app.upload.dir:uploads}")
  private String uploadDir;

  @Value("${app.base.url:http://localhost:4002}")
  private String baseUrl;

  public String saveImage(MultipartFile file) {
    if (file.isEmpty()) throw new RuntimeException("Archivo vacío");
    String ct = file.getContentType();
    if (ct == null || !(ct.equals("image/jpeg") || ct.equals("image/png") || ct.equals("image/webp"))) {
      throw new RuntimeException("Formato no permitido (solo jpg/png/webp)");
    }

    try {
      Files.createDirectories(Path.of(uploadDir));
      String ext = switch (ct) {
        case "image/jpeg" -> ".jpg";
        case "image/png"  -> ".png";
        case "image/webp" -> ".webp";
        default -> "";
      };
      String filename = UUID.randomUUID() + ext;
      Path target = Path.of(uploadDir).resolve(filename);
      Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
      return baseUrl + "/uploads/" + filename; // URL pública
    } catch (IOException e) {
      throw new RuntimeException("Error guardando imagen", e);
    }
  }
}
