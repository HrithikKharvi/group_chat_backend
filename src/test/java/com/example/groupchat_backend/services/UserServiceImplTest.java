package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void buildDefaultUserWithUserId_returnsMockUser(){
        assertTrue(userService.buildDefaultUserWithUserId("test") != null);
    }

    @Test
    void findUserById_findsAndReturnsUser(){
        User mockUser = mock(User.class);

        when(userRepo.findById(anyString())).thenReturn(Optional.of(mockUser));

        assertTrue(userService.findUserById("Test") != null);
    }

    @Test
    void findUserById_findsAndReturnsNullWhenNoUserFoundWithId(){
        when(userRepo.findById(anyString())).thenReturn(Optional.empty());

        assertTrue(userService.findUserById("Test") == null);
    }

}
