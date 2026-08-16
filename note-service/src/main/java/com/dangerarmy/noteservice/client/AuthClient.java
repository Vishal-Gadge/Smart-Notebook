package com.dangerarmy.noteservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//Goes to auth-service/** through gateway
@FeignClient(name = "api-gateway", path = "/auth")
public interface AuthClient {

    @GetMapping("/profile")
    ResponseEntity<Map<String, String>> getProfile(
            @RequestHeader("X-User-Id") Long id);

}
