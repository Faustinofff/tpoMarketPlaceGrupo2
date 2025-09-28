package com.marketplace.tpo.demo.controllers.auth;

import com.marketplace.tpo.demo.entity.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role; // ENUM, no String
}

