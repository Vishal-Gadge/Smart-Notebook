package com.dangerarmy.noteservice.config;

import com.dangerarmy.noteservice.dto.UserDto;
import com.dangerarmy.noteservice.model.MyUserDetails;
import com.dangerarmy.noteservice.service.Impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("should not method running for :{}",path);
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("<<< JWT Filter running for :{}",request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        log.info("token is from postman: {}",token);

        if(token == null && request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if("jwt".equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if(token != null){
            try {
                Claims claims = jwtService.extractClaims(token);
                UserDto user = jwtService.extractUserDetails(claims);
                log.info("User dto is: {}",user);

                if (SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = new MyUserDetails(user);
                    log.info("userDetails object is created");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }catch (Exception e){
                log.error("JWT validation failed: {}",e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
