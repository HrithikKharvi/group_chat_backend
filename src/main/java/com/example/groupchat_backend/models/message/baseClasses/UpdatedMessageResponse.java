package com.example.groupchat_backend.models.message.baseClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatedMessageResponse {
    private String uniqueId;
    private LocalDateTime sentOn;
    private String sentBy;
    private String sentById;
    private String repliedToMessageId;
    private String groupId;
    private String groupName;
}
