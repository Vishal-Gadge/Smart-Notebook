package com.dangerarmy.authservice.exception;

public class WeakPasswordException extends RuntimeException{
    public WeakPasswordException(String message){
        super(message);
    }
}
