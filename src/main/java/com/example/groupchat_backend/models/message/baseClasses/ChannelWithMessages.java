package com.example.groupchat_backend.models.message.baseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelWithMessages<T extends MessagesMetaData<?>>{
    private String chanelName;
    private String channelId;
    private T messageMetaData;
}
