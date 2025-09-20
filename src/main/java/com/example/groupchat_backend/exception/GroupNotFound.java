package com.example.groupchat_backend.exception;

import com.example.groupchat_backend.exception.baseClasses.NotFoundException;

public class GroupNotFound extends NotFoundException {
    public GroupNotFound(String userNotFoundError){
        super(userNotFoundError);
    }
}
