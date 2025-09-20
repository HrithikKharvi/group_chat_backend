package com.example.groupchat_backend.exception.baseClasses;

public class BadRequestException extends Exception{
    public BadRequestException(String exception){
        super(exception);
    }
}
