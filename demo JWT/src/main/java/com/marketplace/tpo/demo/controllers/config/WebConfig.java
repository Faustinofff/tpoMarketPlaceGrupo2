package com.marketplace.tpo.demo.controllers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get("uploads"); // carpeta local en el root del proyecto
    String uploadPath = uploadDir.toFile().getAbsolutePath();
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:" + uploadPath + "/");
  }
}
