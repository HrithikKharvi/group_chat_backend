package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.group.UserGroupMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface GroupUserRepo extends JpaRepository<UserGroupMapping, String> {

    Page<UserGroupMapping> findByUserId(String userId, Pageable pageable);
    Optional<UserGroupMapping> findByUserIdAndGroupId(String userId, String groupId);

}
