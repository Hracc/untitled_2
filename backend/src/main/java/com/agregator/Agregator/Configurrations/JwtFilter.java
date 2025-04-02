package com.agregator.Agregator.Configurrations;

import com.agregator.Agregator.Entity.User;
import com.agregator.Agregator.Repositories.UserRepository;
import com.agregator.Agregator.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request); // Извлекаем токен из заголовка запроса

        logger.info("Extracted token: {}", token);

        if (token != null && jwtService.isValidToken(token)) {

            String username = jwtService.extractUsername(token); // Извлекаем имя пользователя из токена
            User user;
            if (userRepository.findByEmail(username).isEmpty()) {
                logger.warn("User {} не найден в базе данных. Токен не верен", username);
                SecurityContextHolder.clearContext(); // Убираем аутентификацию
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Пользователь не найден");
                return;
            }


            String role = jwtService.extractRole(token); // Извлекаем роль



            logger.info("Valid token, username: {}, ROLE: {}", username, role);

            // Если токен валиден, устанавливаем аутентификацию
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

            logger.info("Current authentication: {}", currentAuth);
        }else {
            logger.warn("Invalid token or no token provided.");
        }

        filterChain.doFilter(request, response); // Пропускаем запрос дальше
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Извлекаем токен из заголовка Authorization
        }
        return null;
    }
}



