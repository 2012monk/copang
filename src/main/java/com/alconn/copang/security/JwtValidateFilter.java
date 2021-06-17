package com.alconn.copang.security;

import com.alconn.copang.enums.Headers;
import com.alconn.copang.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtValidateFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService service;

    public JwtValidateFilter(CustomUserDetailsService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = HttpUtils.getHeader(request, Headers.AUTHORIZATION);
        token.ifPresent(s -> SecurityContextHolder.getContext().setAuthentication(service.getAuthentication(s)));
        filterChain.doFilter(request, response);
    }
}
