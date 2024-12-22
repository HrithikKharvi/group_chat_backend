package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.TempTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempTableRepo extends JpaRepository<TempTable, String> {
}
