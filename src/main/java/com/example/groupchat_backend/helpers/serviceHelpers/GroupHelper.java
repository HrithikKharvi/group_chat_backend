package com.example.groupchat_backend.helpers.serviceHelpers;

import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessagePageResponse;

import java.util.List;
import java.util.function.Function;

public final class GroupHelper {
    public static Function<List<GroupChannelWithMessages>, GroupMessagePageResponse> BUILD_GROUP_MESSAGE_RESPONSE = groupChannelWithMessages -> groupChannelWithMessages != null ?
            GroupMessagePageResponse.builder().groupsWithMessages(groupChannelWithMessages).build() : GroupMessagePageResponse.builder().build();
}
