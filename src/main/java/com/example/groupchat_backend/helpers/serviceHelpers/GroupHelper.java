package com.example.groupchat_backend.helpers.serviceHelpers;

import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessage;
import com.example.groupchat_backend.models.message.GroupMessagePageResponse;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;

import java.util.List;
import java.util.function.Function;

public final class GroupHelper {

    public static Function<UserGroupMapping, GroupChannelWithMessages> BUILD_GROUP_CHANNEL_WITH_MESSAGE = userGroupMapping  -> {
        return userGroupMapping != null ? GroupChannelWithMessages.builder().chanelName(userGroupMapping.getGroupName()).channelId(userGroupMapping.getGroupId()).build() : GroupChannelWithMessages.builder().build();
    };


    public static Function<List<GroupMessage>, GroupMessagesMetaData> BUILD_MESSAGE_META_DATA = groupMessage -> GroupMessagesMetaData.builder()
            .messages(groupMessage)
            .totalPages(0).build();

    public static Function<List<GroupChannelWithMessages>, GroupMessagePageResponse> BUILD_GROUP_MESSAGE_RESPONSE = groupChannelWithMessages -> groupChannelWithMessages != null ?
            GroupMessagePageResponse.builder().groupsWithMessages(groupChannelWithMessages).build() : GroupMessagePageResponse.builder().build();
}
