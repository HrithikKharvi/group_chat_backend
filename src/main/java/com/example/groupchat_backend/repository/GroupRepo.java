package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, String> {
}
