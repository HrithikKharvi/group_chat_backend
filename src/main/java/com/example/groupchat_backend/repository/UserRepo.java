package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
