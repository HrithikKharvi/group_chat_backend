package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.UserRepo;
import com.example.groupchat_backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.example.groupchat_backend.helpers.shared.AppFunctions.IS_EMPTY_STRING;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    public User buildDefaultUserWithUserId(String userId){
        return User.builder()
                .id(userId)
                .build();
    }


    public User findUserById(String userId){
        Optional<User> foundUser = userRepo.findById(userId);

        return foundUser.orElse(null);
    }
}
