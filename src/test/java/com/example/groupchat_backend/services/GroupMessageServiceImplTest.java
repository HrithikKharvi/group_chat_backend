package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessage;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.repository.GroupMessagesRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GroupMessageServiceImplTest {
    @Spy
    @InjectMocks
    private GroupMessageServiceImpl groupMessageService;

    @Mock
    private GroupService groupService;

    @Mock
    private GroupMessagesRepo groupMessagesRepo;


    @Test
    void getChannelsWithMessages_ReturnsMessagesList(){
        when(groupService.getAllGroupsWithPage(anyString(), any(Pageable.class))).thenReturn(Mono.just(mock(Page.class)));
        doReturn(Flux.fromIterable(new ArrayList<>())).when(groupMessageService).buildGroupChannelsWithMessages(anyList(), anyInt(), anyInt());
        doReturn(mock(MessagePageResponse.class)).when(groupMessageService).buildMessagePageResponseForTheGroups(any(), anyList());

        MessagePageResponse response = groupMessageService.getChannelsWithMessages("string", 0, 100).block();

        assertNotNull(response);
    }
    @Test
    void buildGroupChannelsWithMessages_returnsGroupChannelsWithMessageResponse() {
        Page<GroupMessage> mockPage = mock(Page.class);
        GroupMessagesMetaData mockMetaData = mock(GroupMessagesMetaData.class);
        GroupChannelWithMessages mockChannel = mock(GroupChannelWithMessages.class);

        doReturn(mockPage).when(groupMessageService).getAllMessageForGroupByIdForPage(anyInt(), anyInt(), anyString());
        doReturn(mockMetaData).when(groupMessageService).buildGroupMessageMetaDataFromMessagePage(any());
        when(groupService.buildGroupChannelsWithMessagesFromGroups(any(), eq(mockMetaData))).thenReturn(mockChannel);

        List<UserGroupMapping> userGroups = new ArrayList<>();
        userGroups.add(UserGroupMapping.builder().groupId("group123").build());

        List<GroupChannelWithMessages> groupChannels =
                groupMessageService.buildGroupChannelsWithMessages(userGroups, 0, 100).collectList().block();

        assertNotNull(groupChannels);
        assertEquals(1, groupChannels.size());
    }


    @Test
    void getAllMessageForGroupByIdForPage_returnAllMessagesForGroup(){
        Page<GroupMessage> messagesPage = mock(Page.class);
        doReturn(messagesPage).when(groupMessageService).getAllMessageForGroupById(anyString(), any(Pageable.class));

        Page<GroupMessage> groupMessages= groupMessageService.getAllMessageForGroupByIdForPage(0, 100, "test");

        assertNotNull(groupMessages);
    }

    @Test
    void getAllMessageForGroupById_returnAllMessagesFromDb(){
        when(groupMessagesRepo.findAllByGroupId(anyString(), any(Pageable.class))).thenReturn(mock(Page.class));

        Page<GroupMessage> messagePage = groupMessageService.getAllMessageForGroupById("test", mock(Pageable.class));

        assertNotNull(messagePage);
    }

}
