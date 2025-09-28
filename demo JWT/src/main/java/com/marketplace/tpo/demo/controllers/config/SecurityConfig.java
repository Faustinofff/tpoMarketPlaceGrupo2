package com.marketplace.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                        AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
}

        @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()                  // PÚBLICO
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/products/**").hasAnyRole("SELLER","ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/products/**").hasAnyRole("SELLER","ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/products/**").hasAnyRole("SELLER","ADMIN")
                .requestMatchers("/cart/**").hasRole("USER")
                .anyRequest().authenticated()
        )
        .sessionManagement(s -> s.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
return http.build();
}}
 //comentario

