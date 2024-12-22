package com.example.groupchat_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="temp_table")
public class TempTable {

    @Id
    @Column(name="username")
    private String username;

    public TempTable() {
    }

    public TempTable(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
