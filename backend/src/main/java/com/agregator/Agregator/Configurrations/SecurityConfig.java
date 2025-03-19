package com.agregator.Agregator.Configurrations;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Если API, можно отключить CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/send-code", "/auth/verify", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/index.html#/").permitAll() // Доступ без авторизации
                        .requestMatchers("/Service/**","/customer/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated() // Все остальные запросы требуют верификации
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    //.addFilterBefore(new SessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable()) // Отключаем форму логина
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Делаем API stateless (без сессий)
                .build();
    }
}

