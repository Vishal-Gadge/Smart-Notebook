package com.dangerarmy.authservice.controller;

import java.util.Map;

import com.dangerarmy.authservice.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dangerarmy.authservice.service.LoginService;

@RestController
@Slf4j
@RequestMapping("/req")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login/verify")
    public ResponseEntity<?> verifyUserLogin(@RequestBody UserRequest user, HttpServletRequest request,HttpServletResponse response){
        loginService.login(user, request, response);
        return ResponseEntity.ok(Map.of("message","Login successful"));
    }
}
