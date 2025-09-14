package com.example.groupchat_backend.models.message;

import com.example.groupchat_backend.models.message.baseClasses.CommonMessageData;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//specific to the group_chat
//where as we also can create 1 for PersonalMessage with sentToId field in it
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Data
@Table(name="messages")
public class GroupMessage {
    @Id
    @Column(name="unique_id")
    private String uniqueId;
    @Embedded
    @JsonUnwrapped
    private CommonMessageData commonMessageData;
    @Column(name="group_id")
    private String groupId;

    @Column(name="group_name")
    private String groupName;
}
