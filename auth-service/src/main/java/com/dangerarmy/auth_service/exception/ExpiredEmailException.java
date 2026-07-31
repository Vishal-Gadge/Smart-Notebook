package com.dangerarmy.auth_service.exception;

public class ExpiredEmailException extends RuntimeException{
    public ExpiredEmailException (String message){
        super(message);
    }
}
