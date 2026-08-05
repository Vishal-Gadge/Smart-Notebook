package com.dangerarmy.noteservice.service.Impl;

import com.dangerarmy.noteservice.dto.UserDto;
import com.dangerarmy.noteservice.model.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class JwtServiceImpl {

    private final SecretKey key;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Jwt expired , go login again",e);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid jwt",e);
        }
    }

    public UserDto extractUserDetails(Claims claims){
        return new UserDto(((Number) claims.get("id")).longValue(), (String) claims.get("username"),
                claims.getSubject(), (List<String>) claims.get("roles"));
    }

    public MyUserDetails extractMyUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication.getPrincipal() instanceof MyUserDetails)){
            throw new RuntimeException("User not authenticated");
        }
        return (MyUserDetails) authentication.getPrincipal();
    }
}