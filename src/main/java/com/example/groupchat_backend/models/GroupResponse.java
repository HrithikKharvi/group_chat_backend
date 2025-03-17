package com.example.groupchat_backend.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GroupResponse {

    private String groupId;
    private String groupName;
    private LocalDateTime createdOn;

}
