package com.dangerarmy.authservice.exception;

public class UserAlreadyVerifiedException extends RuntimeException{
    public UserAlreadyVerifiedException(String message){
        super(message);
    }
}
