package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepo extends JpaRepository<Message, String> {

    List<Message> findAllByGroupId(String groupId);
    Message findByUniqueId(String uniqueId);

}
