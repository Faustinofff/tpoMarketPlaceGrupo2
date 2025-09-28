package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.controllers.auth.AuthenticationRequest;
import com.marketplace.tpo.demo.controllers.auth.AuthenticationResponse;
import com.marketplace.tpo.demo.controllers.auth.RegisterRequest;
import com.marketplace.tpo.demo.controllers.config.JwtService;
import com.marketplace.tpo.demo.entity.Role;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // si no mandan role, por defecto USER
        Role role = request.getRole() == null ? Role.USER : request.getRole();

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)                         // <— usa el role recibido (incluye SELLER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}

