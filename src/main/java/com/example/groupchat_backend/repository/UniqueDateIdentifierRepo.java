package com.example.groupchat_backend.repository;


import com.example.groupchat_backend.models.repository.UniqueDateIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniqueDateIdentifierRepo extends JpaRepository<UniqueDateIdentifier, String> {
}
