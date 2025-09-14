package com.example.groupchat_backend.models.message;

import com.example.groupchat_backend.models.message.baseClasses.MessagesMetaData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class GroupMessagesMetaData extends MessagesMetaData<GroupMessage> {

}
