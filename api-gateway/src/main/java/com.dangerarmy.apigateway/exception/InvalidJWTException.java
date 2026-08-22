package com.dangerarmy.apigateway.exception;

public class InvalidJWTException extends RuntimeException{
    public InvalidJWTException(String message){
        super(message);
    }
}
