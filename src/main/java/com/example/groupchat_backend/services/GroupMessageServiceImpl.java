package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessage;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.repository.GroupMessagesRepo;
import com.example.groupchat_backend.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.groupchat_backend.constants.CommonAppData.MESSAGE_400_PER_PAGE;
import static com.example.groupchat_backend.helpers.serviceHelpers.GroupHelper.*;
import static com.example.groupchat_backend.helpers.shared.AppFunctions.GET_NEXT_INTEGER_COUNT;

@Service
@RequiredArgsConstructor
public class GroupMessageServiceImpl implements MessageService {

    private final GroupService groupService;

    private final GroupMessagesRepo groupMessagesRepo;


    @Override
    public Mono<MessagePageResponse> getChannelsWithMessages(String userId, Integer currentPage, int pageSize) {
        int nextPage = GET_NEXT_INTEGER_COUNT.apply(currentPage);
        PageRequest pageRequest = PageRequest.of(nextPage, pageSize);


        return groupService.getAllGroupsWithPage(userId, pageRequest)
                .flatMap(groupPageData -> {
                    List<UserGroupMapping> userGroupMappings = groupPageData.getContent();

                    return buildGroupChannelsWithMessages(userGroupMappings, 0, MESSAGE_400_PER_PAGE)
                            .collectList()
                            .map(groupChannelWithMessagesList -> buildMessagePageResponseForTheGroups(groupPageData, groupChannelWithMessagesList));
                });
    }

    private Flux<GroupChannelWithMessages> buildGroupChannelsWithMessages(List<UserGroupMapping> userGroupMapping, int messagePage, int messagePageCount){
        return Flux.fromIterable(userGroupMapping.stream()
                .map(userGroup -> {
                    Page<GroupMessage> groupMessagesPage = getAllMessageForGroupByIdForPage(messagePage, messagePageCount, userGroup.getGroupId());
                    return groupService.buildGroupChannelsWithMessagesFromGroups(userGroup, buildGroupMessageMetaDataFromMesssagePage(groupMessagesPage));
                }).toList());
    }

    public Page<GroupMessage> getAllMessageForGroupByIdForPage(int pageNumber, int maxPageCount, String groupId){
        Pageable pageable = PageRequest.of(pageNumber, maxPageCount);
        Page<GroupMessage> messageForPage = getAllMessageForGroupById(groupId, pageable);
        return messageForPage;
    }

    private Page<GroupMessage> getAllMessageForGroupById(String groupId, Pageable pageable){
        return groupMessagesRepo.findAllByGroupId(groupId, pageable);
    }

    public MessagePageResponse buildMessagePageResponseForTheGroups(Page<UserGroupMapping> userGroupMappingPage, List<GroupChannelWithMessages> userMessagesForGroup){
        MessagePageResponse response = BUILD_GROUP_MESSAGE_RESPONSE.apply(userMessagesForGroup);
        response.setCount(userGroupMappingPage.getTotalElements());
        response.setTotalPages(userGroupMappingPage.getTotalPages());
        response.setPage(userGroupMappingPage.getPageable().getPageNumber());

        return response;
    }

    public GroupMessagesMetaData buildGroupMessageMetaDataFromMesssagePage(Page<GroupMessage> messagePageData){
        return GroupMessagesMetaData.builder()
                .messages(messagePageData.getContent())
                .count(messagePageData.getTotalElements())
                .totalPages(messagePageData.getTotalPages())
                .build();
    }

}
