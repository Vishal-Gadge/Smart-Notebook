package com.dangerarmy.apigateway.exception;

public class ExpiredJWTException extends RuntimeException{
    public ExpiredJWTException(String message){
        super(message);
    }
}
