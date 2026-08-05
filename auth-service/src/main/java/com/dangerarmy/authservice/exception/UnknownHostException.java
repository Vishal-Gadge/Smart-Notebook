package com.dangerarmy.authservice.exception;

public class UnknownHostException extends RuntimeException{
    public UnknownHostException(String message){
        super(message);
    }
}
