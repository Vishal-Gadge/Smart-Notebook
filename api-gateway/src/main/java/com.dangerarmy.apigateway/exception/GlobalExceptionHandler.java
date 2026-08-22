package com.dangerarmy.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJWTException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwt(ExpiredJWTException e){
        return ResponseEntity.status(HttpStatus.GONE)
                .body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJwt(InvalidJWTException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }
}
