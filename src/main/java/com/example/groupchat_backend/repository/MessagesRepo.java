package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.Message;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MessagesRepo extends JpaRepository<Message, String> {
}
