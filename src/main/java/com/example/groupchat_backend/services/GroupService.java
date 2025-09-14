package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.repository.GroupUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupUserRepo groupUserRepo;

    public Mono<Page<UserGroupMapping>> getAllGroupsWithPage(String userId, Pageable pageable){
        return Mono.fromCallable(() -> groupUserRepo.findByUserId(userId, pageable));
    }

    public GroupChannelWithMessages buildGroupChannelsWithMessagesFromGroups(UserGroupMapping group, GroupMessagesMetaData groupMessagePage){
        return  this.buildGroupChannelsWithMessageFromGroupMessages(group, groupMessagePage);

    }

    public GroupChannelWithMessages buildGroupChannelsWithMessageFromGroupMessages(UserGroupMapping userGroupMapping, GroupMessagesMetaData messagesMetaData){
        return GroupChannelWithMessages.builder()
                .chanelName(userGroupMapping.getGroupName())
                .channelId(userGroupMapping.getGroupId())
                .messageMetaData(messagesMetaData)
                .build();
    }

}
