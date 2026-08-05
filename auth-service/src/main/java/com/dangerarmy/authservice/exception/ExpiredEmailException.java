package com.dangerarmy.authservice.exception;

public class ExpiredEmailException extends RuntimeException{
    public ExpiredEmailException (String message){
        super(message);
    }
}
