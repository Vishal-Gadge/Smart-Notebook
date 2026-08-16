package com.dangerarmy.noteservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dangerarmy.noteservice.client.AuthClient;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final AuthClient authClient;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> profile(@RequestHeader("X-User-Id") Long id){
        return authClient.getProfile(id);
    }

    @GetMapping("/health")
    public void checkHealth(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write("I am ok");
    }

}
