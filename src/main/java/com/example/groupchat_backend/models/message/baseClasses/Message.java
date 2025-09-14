package com.example.groupchat_backend.models.message.baseClasses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="messages")
public class Message {

    @Id
    @Column(name="unique_id")
    private String uniqueId;

    @Embedded
    private CommonMessageData messageData;
}
