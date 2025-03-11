/*
package com.agregator.Agregator.Configurrations;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class SessionAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, jakarta.servlet.FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            String phone = (String) session.getAttribute("authenticatedPhone");
            String secureSessionId = (String) session.getAttribute("secureSessionId");
            String savedIp = (String) session.getAttribute("authenticatedIp");
            String savedUserAgent = (String) session.getAttribute("authenticatedUserAgent");

            // Получаем текущие IP и User-Agent
            String currentIp = getClientIp(httpRequest);
            String currentUserAgent = httpRequest.getHeader("User-Agent");

            if (phone != null && secureSessionId != null && savedIp != null && savedUserAgent != null) {
                if (savedIp.equals(currentIp) && savedUserAgent.equals(currentUserAgent)) {
                    // Аутентифицируем пользователя
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            phone, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Уничтожаем сессию при попытке подмены IP или User-Agent
                    session.invalidate();
                    System.out.println("Попытка взлома! Сессия уничтожена.");
                }
            }
        }

        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
*/
