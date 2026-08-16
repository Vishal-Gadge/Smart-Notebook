package com.dangerarmy.noteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequest(InvalidRequestException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message",e.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointer(NullPointerException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message",e.getMessage()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExist(AlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message",e.getMessage()));
    }

    @ExceptionHandler(OutOfLimitExecption.class)
    public ResponseEntity<Map<String, String>> handleOutOfLimit(OutOfLimitExecption e){
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(Map.of("message",e.getMessage()));
    }
}
