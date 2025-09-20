package com.example.groupchat_backend.repository;


import com.example.groupchat_backend.models.repository.UniqueDateIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniqueDateIdentifierRepo extends JpaRepository<UniqueDateIdentifier, String> {
    public Optional<UniqueDateIdentifier> findByDateAndUserId(String dateField, String userId);
}
