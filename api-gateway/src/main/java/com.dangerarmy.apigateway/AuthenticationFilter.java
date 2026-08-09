package com.dangerarmy.apigateway;

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
        log.info("Authentication filter running for path :{}",path);

        // 1. Skip JWT check for /auth/req which are login, signup, forgot pass and static rss
        if (path.startsWith("/auth/req/") || path.startsWith("/auth/js") || path.startsWith("/auth/css")
            || path.startsWith("/auth/images") || path.startsWith("/auth/html")) {
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Internal-Secret", internalSecret)
                    .build();

            log.warn("jwt filter was skipped for path :{}",path);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }

        // 2. Get token from COOKIE
        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst("jwt");

        if (tokenCookie == null) {
            log.warn("Missing token cookie for path: {}", path);
            return onError(exchange, "Missing Token Cookie", HttpStatus.UNAUTHORIZED);
        }

        String token = tokenCookie.getValue();
        log.info("token get from cookie :{}",token);

        // 3. Validate token and take userId
        Long userId = ((Number) jwtUtil.extractClaims(token).get("id")).longValue();
        log.info("user id from token is :{}",userId);

        // 4. Role check for admin routes
        if (path.startsWith("/auth/admin") && !jwtUtil.hasRole(token, "ADMIN")) {
            log.warn("Forbidden: ADMIN role required for path: {}", path);
            return onError(exchange, "Forbidden: ADMIN role required", HttpStatus.FORBIDDEN);
        }

        // 5. Add userId to headers so downstream services can use it
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Internal-Secret",internalSecret)
                .header("X-User-Id",String.valueOf(userId))
                .build();

        log.info("authentication filter done his work");
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
        log.warn("get order method run : -1");
        return -1; // Run this filter first
    }
}