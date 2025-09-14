package com.example.groupchat_backend.models.message;

import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class GroupMessagePageResponse extends MessagePageResponse<GroupChannelWithMessages> {
    public GroupMessagePageResponse(int count, int page, int totalPages, List<GroupChannelWithMessages> messagesGroupData){
        super(count, page, totalPages, messagesGroupData);
    }
}
