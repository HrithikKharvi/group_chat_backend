package com.example.groupchat_backend.models.repository;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawMessageDTO {

    @NotEmpty
    private String messageText;
    @NotEmpty
    private String sentByUserId;
    @NotEmpty
    private String sentToGroupId;
    private String repliedToMessageId;

}
