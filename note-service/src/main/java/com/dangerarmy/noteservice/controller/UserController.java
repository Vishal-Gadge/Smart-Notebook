package com.dangerarmy.noteservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("X-User-Id") String userId){
        log.info("Received X-User-Id: {}",userId);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok("I am ok");
    }

    @GetMapping("")
    public ResponseEntity<?> isPrivate(){
        return ResponseEntity.ok("this is private block");
    }
}
