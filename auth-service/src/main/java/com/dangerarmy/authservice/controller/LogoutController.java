package com.dangerarmy.authservice.controller;

import com.dangerarmy.authservice.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/req")
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        logoutService.logout(servletRequest, servletResponse);
        return "login";
    }
}
