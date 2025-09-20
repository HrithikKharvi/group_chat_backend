package com.example.groupchat_backend.exception;

import com.example.groupchat_backend.exception.baseClasses.NotFoundException;

public class UserNotFound extends NotFoundException {
    public UserNotFound(String groupNotFoundError){
        super(groupNotFoundError);
    }
}
