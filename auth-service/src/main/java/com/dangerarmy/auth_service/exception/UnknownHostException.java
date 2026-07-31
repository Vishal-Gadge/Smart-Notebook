package com.dangerarmy.auth_service.exception;

public class UnknownHostException extends RuntimeException{
    public UnknownHostException(String message){
        super(message);
    }
}
