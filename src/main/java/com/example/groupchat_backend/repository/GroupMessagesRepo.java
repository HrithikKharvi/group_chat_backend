package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.message.GroupMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMessagesRepo extends JpaRepository<GroupMessage, String> {

    @Query("SELECT gm FROM GroupMessage gm WHERE gm.groupId = :groupId")
    List<GroupMessage> findAllByGroupId(@Param("groupId") String groupId);
    Page<GroupMessage> findAllByGroupId(@Param("groupId") String groupId, Pageable pageable);
    GroupMessage findByUniqueId(String uniqueId);

}
