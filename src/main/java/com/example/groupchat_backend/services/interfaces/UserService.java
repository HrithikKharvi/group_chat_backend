package com.example.groupchat_backend.services.interfaces;

import com.example.groupchat_backend.models.user.User;

public interface UserService {
    public User buildDefaultUserWithUserId(String userId);
}
