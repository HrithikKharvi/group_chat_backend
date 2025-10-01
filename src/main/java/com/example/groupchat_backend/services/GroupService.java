package com.example.groupchat_backend.services;

import com.example.groupchat_backend.exception.UserNotFound;
import com.example.groupchat_backend.exception.baseClasses.BadRequestException;
import com.example.groupchat_backend.exception.baseClasses.NotFoundException;
import com.example.groupchat_backend.helpers.serviceHelpers.DatabaseAccessors;
import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.repository.GroupRepo;
import com.example.groupchat_backend.repository.GroupUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupUserRepo groupUserRepo;
    private final GroupRepo groupRepo;

    private final DatabaseAccessors databaseAccessors;

    public Mono<Page<UserGroupMapping>> getAllGroupsForUserWithPage(String userId, Pageable pageable){
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

    public Group findGroupById(String groupId){
        Optional<Group> foundGroup = groupRepo.findById(groupId);

        return foundGroup.orElse(null);
    }

    public UserGroupMapping findUserGroupMapping(String userId, String groupId){
        Optional<UserGroupMapping> foundGroup = groupUserRepo.findByUserIdAndGroupId(userId, groupId);

        return foundGroup.orElse(null);
    }

    public Mono<Group> validateAndGetGroupInfo(String userId, String groupId){
        if(databaseAccessors.findUserByUserId(userId) == null)return Mono.error(new UserNotFound("User with provided id is not found"));
        if(databaseAccessors.findUserGroupMapping(userId, groupId) == null)return Mono.error(new BadRequestException("User is not a part of the group"));

        return  Mono.just(databaseAccessors.findGroupByGroupId(groupId)).switchIfEmpty(Mono.error(new NotFoundException("User Group not found")));
    }

}
