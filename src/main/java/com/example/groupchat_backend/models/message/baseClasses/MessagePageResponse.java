package com.example.groupchat_backend.models.message.baseClasses;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessagePageResponse<T extends ChannelWithMessages<? extends MessagesMetaData<?>>> {
    private long count;
    private int page;
    private int totalPages;
    private List<T> groupsWithMessages;

    public void addNewDataToMessagesDataList(T messageData){
        if(this.groupsWithMessages == null)this.groupsWithMessages = new ArrayList<>();
        this.groupsWithMessages.add(messageData);
    }
}
