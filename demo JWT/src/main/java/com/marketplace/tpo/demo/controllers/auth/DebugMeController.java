package com.marketplace.tpo.demo.controllers.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class DebugMeController {
  @GetMapping("/me")
  public Object me(Authentication auth) {
    if (auth == null) return "anonymous";
    return new Object() {
      public final String name = auth.getName();
      public final Object authorities = auth.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    };
  }
}
