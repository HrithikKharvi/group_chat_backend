package com.example.groupchat_backend.models.message;

import com.example.groupchat_backend.models.message.baseClasses.ChannelWithMessages;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class GroupChannelWithMessages extends ChannelWithMessages<GroupMessagesMetaData> {
}
