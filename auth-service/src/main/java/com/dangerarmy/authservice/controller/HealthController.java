package com.dangerarmy.authservice.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dangerarmy.authservice.model.UserModel;
import com.dangerarmy.authservice.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final UserRepo userRepo;

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok("I am good");
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> getProfile(@RequestHeader("X-User-Id") Long id){
        Optional<UserModel> user = userRepo.findById(Math.toIntExact(id));
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                        .body(Map.of("message", "User not found"));
        }else{
            return ResponseEntity.ok(Map.of(
                "username" , user.orElseThrow().getUsername(),
                "email", user.orElseThrow().getEmail()
            ));
        }
    }
}
