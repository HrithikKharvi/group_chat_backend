package com.example.groupchat_backend.exception.baseClasses;

public class NotFoundException extends Exception{
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}
