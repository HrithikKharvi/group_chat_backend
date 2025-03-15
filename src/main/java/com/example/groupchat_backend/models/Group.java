package com.example.groupchat_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name="chat_groups")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="group_name")
    private String groupName;

    @Column(name="created_on")
    private LocalDateTime createdOn;

}
