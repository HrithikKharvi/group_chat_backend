package com.example.groupchat_backend.services;

import com.example.groupchat_backend.exception.GroupNotFound;
import com.example.groupchat_backend.exception.UserNotFound;
import com.example.groupchat_backend.exception.baseClasses.BadRequestException;
import com.example.groupchat_backend.helpers.shared.AppFunctions;
import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.*;
import com.example.groupchat_backend.models.message.baseClasses.CommonMessageData;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.GroupMessagesRepo;
import com.example.groupchat_backend.services.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.groupchat_backend.constants.CommonAppData.MESSAGE_400_PER_PAGE;
import static com.example.groupchat_backend.helpers.serviceHelpers.GroupHelper.BUILD_GROUP_MESSAGE_RESPONSE;
import static com.example.groupchat_backend.helpers.shared.AppFunctions.GET_NEXT_INTEGER_COUNT;

@Service
public class GroupMessageServiceImpl implements MessageService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMessagesRepo groupMessagesRepo;

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private UniqueDataBignumberService uniqueDataBignumberService;

    public Mono<UpdatedGroupMessageResponse> postMessageIntoGroup(RawGroupMessageDTO userSentRawMessage){
        User user = userService.findUserById(userSentRawMessage.getSentByUserId());
        if(user == null)return Mono.error(new UserNotFound("User not found with the provided ID : " + userSentRawMessage.getSentByUserId()));
        Group group = groupService.findGroupById(userSentRawMessage.getSentToGroupId());
        if(group == null)return Mono.error(new GroupNotFound("User group not found with the provided ID : " + userSentRawMessage.getSentByUserId()));
        UserGroupMapping userGroupMapping = groupService.findUserGroupMapping(userSentRawMessage.getSentByUserId(), userSentRawMessage.getSentToGroupId());
        if(userGroupMapping == null)return Mono.error(new BadRequestException("User not mapped to the particular group"));

        GroupMessage groupMessage = this.buildGroupMessageFromRawGroupMessage(userSentRawMessage, user, group);

        return Mono.just(this.mapGroupMessageToUpdatedGroupMessage(this.saveMessageIntoDb(groupMessage)));
    }


    @Override
    public Mono<MessagePageResponse> getChannelsWithMessages(String userId, Integer currentPage, int pageSize) {
        int nextPage = GET_NEXT_INTEGER_COUNT.apply(currentPage);
        PageRequest pageRequest = PageRequest.of(nextPage, pageSize);
        return groupService.getAllGroupsForUserWithPage(userId, pageRequest)
                .flatMap(groupPageData -> {
                    List<UserGroupMapping> userGroupMappings = groupPageData.getContent();

                    return buildGroupChannelsWithMessages(userGroupMappings, 0, MESSAGE_400_PER_PAGE)
                            .collectList()
                            .map(groupChannelWithMessagesList -> buildMessagePageResponseForTheGroups(groupPageData, groupChannelWithMessagesList));
                });
    }

    public Flux<GroupChannelWithMessages> buildGroupChannelsWithMessages(List<UserGroupMapping> userGroupMapping, int messagePage, int messagePageCount){
        return Flux.fromIterable(userGroupMapping.stream()
                .map(userGroup -> {
                    Page<GroupMessage> groupMessagesPage = getAllMessageForGroupByIdForPage(messagePage, messagePageCount, userGroup.getGroupId());
                    return groupService.buildGroupChannelsWithMessagesFromGroups(userGroup, buildGroupMessageMetaDataFromMessagePage(groupMessagesPage));
                }).toList());
    }

    public Page<GroupMessage> getAllMessageForGroupByIdForPage(int pageNumber, int maxPageCount, String groupId){
        Pageable pageable = PageRequest.of(pageNumber, maxPageCount);
        Page<GroupMessage> messageForPage = getAllMessageForGroupById(groupId, pageable);
        return messageForPage;
    }

    public Page<GroupMessage> getAllMessageForGroupById(String groupId, Pageable pageable){
        return groupMessagesRepo.findAllByGroupId(groupId, pageable);
    }

    public MessagePageResponse buildMessagePageResponseForTheGroups(Page<UserGroupMapping> userGroupMappingPage, List<GroupChannelWithMessages> userMessagesForGroup){
        MessagePageResponse response = BUILD_GROUP_MESSAGE_RESPONSE.apply(userMessagesForGroup);
        response.setCount(userGroupMappingPage.getTotalElements());
        response.setTotalPages(userGroupMappingPage.getTotalPages());
        response.setPage(userGroupMappingPage.getPageable().getPageNumber());

        return response;
    }

    public GroupMessagesMetaData buildGroupMessageMetaDataFromMessagePage(Page<GroupMessage> messagePageData){
        return GroupMessagesMetaData.builder()
                .messages(messagePageData.getContent())
                .count(messagePageData.getTotalElements())
                .totalPages(messagePageData.getTotalPages())
                .build();
    }

    public UpdatedGroupMessageResponse mapGroupMessageToUpdatedGroupMessage(GroupMessage groupMessage){
        if(groupMessage == null)return UpdatedGroupMessageResponse.builder().build();

        return UpdatedGroupMessageResponse.builder()
                .uniqueId(groupMessage.getUniqueId())
                .sentBy(groupMessage.getCommonMessageData().getSentBy())
                .sentById(groupMessage.getCommonMessageData().getSentById())
                .sentOn(groupMessage.getCommonMessageData().getSentOn())
                .groupId(groupMessage.getGroupId())
                .groupName(groupMessage.getGroupName())
                .repliedToMessageId(groupMessage.getCommonMessageData().getRepliedToMessageId())
                .build();
    }

    public GroupMessage saveMessageIntoDb(GroupMessage groupMessage){
        return groupMessagesRepo.save(groupMessage);
    }

    public GroupMessage buildGroupMessageFromRawGroupMessage(RawGroupMessageDTO rawGroupMessageDTO, User user, Group group){
        BigInteger uniqueNextBigIntegerForToday = uniqueDataBignumberService.getNextUniqueDateIdentifier(LocalDate.now().toString(), rawGroupMessageDTO.getSentByUserId());
        String uniqueMessageId = AppFunctions.GENERATE_MESSAGE_UNIQUE_ID(uniqueNextBigIntegerForToday, rawGroupMessageDTO);
        LocalDateTime createdTime = LocalDateTime.now();

        return GroupMessage.builder()
                .uniqueId(uniqueMessageId)
                .commonMessageData(CommonMessageData.builder()
                        .repliedToMessageId(rawGroupMessageDTO.getRepliedToMessageId())
                        .message(rawGroupMessageDTO.getMessageText())
                        .sentBy(user.getName())
                        .sentOn(createdTime)
                        .sentById(user.getId()).build())
                .groupId(group.getId())
                .groupName(group.getGroupName()).build();
    }

}
