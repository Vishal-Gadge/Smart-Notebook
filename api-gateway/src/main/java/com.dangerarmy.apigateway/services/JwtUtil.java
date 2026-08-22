package com.dangerarmy.apigateway.services;

import com.dangerarmy.apigateway.exception.ExpiredJWTException;
import com.dangerarmy.apigateway.exception.InvalidJWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret){
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
            throw new ExpiredJWTException("Jwt token is Expired, Go login");
        } catch (JwtException e) {
            throw new InvalidJWTException("Invalid jwt");
        }
    }

    //Check if token has a specific role
    public boolean hasRole(String token, String role) {
        Claims claims = extractClaims(token);
        // We stored roles as List<String> when creating token
        List<String> roles = claims.get("roles", List.class);
        return roles != null && roles.contains(role);
    }
}