package com.dangerarmy.noteservice.config;

import com.dangerarmy.noteservice.exception.InvalidRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class InternalSecretFilter extends OncePerRequestFilter {

    @Value("${internal.secret}")
    private String internalSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!internalSecret.equals(request.getHeader("X-Internal-Secret"))){
            log.warn("User on {} is trying to access note service externally", request.getRemoteAddr());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{error: Access denied}");
            return;
        }
        log.info("Internal secret filter passed for url :{}",request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
