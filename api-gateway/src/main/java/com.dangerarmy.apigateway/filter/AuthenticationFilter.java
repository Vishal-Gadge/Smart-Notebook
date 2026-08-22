package com.dangerarmy.apigateway.filter;

import com.dangerarmy.apigateway.services.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${internal.secret}")
    private String internalSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. Skip JWT check for /auth/req which are login, signup, forgot pass and static rss
        if (path.startsWith("/auth/req/") || path.startsWith("/auth/js") || path.startsWith("/auth/css")
            || path.startsWith("/auth/images") || path.startsWith("/auth/html")
            || path.startsWith("/notes/health") || path.startsWith("/auth/health")) {
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Internal-Secret", internalSecret)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }

        // 1.1. Skip JWT check if it has X-Internal-Secret means coming from internal services through feign
        String secretHeader = exchange.getRequest().getHeaders().getFirst("X-Internal-Secret");
        if(Objects.equals(secretHeader,internalSecret)){
            return chain.filter(exchange);
        }

        // 2. Get token from COOKIE
        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst("jwt");

        if (tokenCookie == null) {
            return onError(exchange, "Missing Token Cookie", HttpStatus.UNAUTHORIZED);
        }

        String token = tokenCookie.getValue();

        // 3. Validate token and take userId
        Long userId = ((Number) jwtUtil.extractClaims(token).get("id")).longValue();

        // 4. Role check for admin routes
        if (path.startsWith("/auth/admin") && !jwtUtil.hasRole(token, "ADMIN")) {
            return onError(exchange, "Forbidden: ADMIN role required", HttpStatus.FORBIDDEN);
        }

        // 5. Add userId to headers so downstream services can use it
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Internal-Secret",internalSecret)
                .header("X-User-Id",String.valueOf(userId))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        byte[] bytes = ("{\"error\": \"" + err + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1; // Run this filter first
    }
}