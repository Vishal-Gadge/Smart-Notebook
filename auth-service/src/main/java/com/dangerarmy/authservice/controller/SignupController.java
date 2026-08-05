package com.dangerarmy.authservice.controller;

import com.dangerarmy.authservice.dto.SignupRequest;
import com.dangerarmy.authservice.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/req/signup/save")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest user) {
        signupService.signup(user);
        return ResponseEntity.ok(Map.of("message",
                "If this email is not registered, we've sent a verification link. Check inbox/spam or tap Verify Email to resend"));
    }
}