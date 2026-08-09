package com.dangerarmy.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/req")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok("I am good");
    }
}
