package com.example.groupchat_backend.models.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Column(name="created_by")
    private String created_by;

    @Column(name="updated_on")
    private LocalDateTime updated_on;

}
