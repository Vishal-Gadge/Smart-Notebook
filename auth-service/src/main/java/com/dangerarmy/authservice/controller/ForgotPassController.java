package com.dangerarmy.authservice.controller;

import com.dangerarmy.authservice.dto.ForgotPassRequest;
import com.dangerarmy.authservice.dto.UserRequest;
import com.dangerarmy.authservice.service.ForgotPassService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/req")
public class ForgotPassController {

    @Autowired
    private ForgotPassService forgotPassService;

    @PostMapping("/forgotPass")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody UserRequest userRequest,
                                                              HttpServletRequest httpRequest){
        forgotPassService.forgotPassword(userRequest, httpRequest);
        return ResponseEntity.ok(Map.of("message","If User present for the email, OTP has been sent"));
    }

    @PostMapping("/verify/forgotPass")
    public ResponseEntity<Map<String,String>> verifyForgotPass(@RequestBody ForgotPassRequest request,
                                                               HttpServletRequest httpRequest){
        forgotPassService.verifyForgotPass(request.getOtp() , httpRequest);
        return ResponseEntity.ok(Map.of("message","Password has been Successfully changed"));
    }
}
