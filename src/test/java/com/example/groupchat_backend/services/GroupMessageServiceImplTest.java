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
import com.example.groupchat_backend.models.repository.UniqueDateIdentifier;
import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.GroupMessagesRepo;
import com.example.groupchat_backend.repository.UniqueDateIdentifierRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UniqueDataBignumberService uniqueDataBignumberService;

    @Mock
    private UniqueDateIdentifierRepo uniqueDateIdentifierRepo;


    @Test
    void getChannelsWithMessages_ReturnsMessagesList(){
        when(groupService.getAllGroupsForUserWithPage(anyString(), any(Pageable.class))).thenReturn(Mono.just(mock(Page.class)));
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

    @Test
    void postMessageIntoGroup_validRequest_returnsUpdatedGroupMessageResponse() {
        RawGroupMessageDTO rawMessage = RawGroupMessageDTO.builder()
                .sentByUserId("user123")
                .sentToGroupId("group456")
                .messageText("Hello!")
                .build();

        User mockUser = mock(User.class);
        Group mockGroup = mock(Group.class);
        UserGroupMapping mockMapping = mock(UserGroupMapping.class);
        GroupMessage mockGroupMessage = mock(GroupMessage.class);
        UpdatedGroupMessageResponse mockResponse = mock(UpdatedGroupMessageResponse.class);

        when(userService.findUserById("user123")).thenReturn(mockUser);
        when(groupService.findGroupById("group456")).thenReturn(mockGroup);
        when(groupService.findUserGroupMapping("user123", "group456")).thenReturn(mockMapping);
        doReturn(mockGroupMessage).when(groupMessageService).buildGroupMessageFromRawGroupMessage(rawMessage, mockUser, mockGroup);
        doReturn(mockGroupMessage).when(groupMessageService).saveMessageIntoDb(mockGroupMessage);
        doReturn(mockResponse).when(groupMessageService).mapGroupMessageToUpdatedGroupMessage(mockGroupMessage);

        UpdatedGroupMessageResponse result =
                groupMessageService.postMessageIntoGroup(rawMessage).block();

        assertNotNull(result);

    }

    @Test
    void postMessageIntoGroup_validRequest_returnsUpdatedGroupMessageResponseWhenGroupMessageIsNull(){
        UpdatedGroupMessageResponse result =
                groupMessageService.mapGroupMessageToUpdatedGroupMessage(null);

        assertNotNull(result);
    }

    @Test
    void postMessageIntoGroup_userNotFound_throwsUserNotFoundException() {
        RawGroupMessageDTO rawMessage = RawGroupMessageDTO.builder()
                .sentByUserId("missingUser")
                .sentToGroupId("group123")
                .messageText("Hello")
                .build();

        when(userService.findUserById("missingUser")).thenReturn(null);

        Exception wrapper = assertThrows(RuntimeException.class, () -> {
            groupMessageService.postMessageIntoGroup(rawMessage).block();
        });

        Throwable actual = wrapper.getCause();

        assertNotNull(actual);
        assertInstanceOf(UserNotFound.class, actual);
        assertTrue(actual.getMessage().contains("User not found with the provided ID : missingUser"));
    }


    @Test
    void postMessageIntoGroup_groupNotFound_throwsGroupNotFoundException() {
        RawGroupMessageDTO rawMessage = RawGroupMessageDTO.builder()
                .sentByUserId("user123")
                .sentToGroupId("missingGroup")
                .messageText("Hello")
                .build();

        when(userService.findUserById("user123")).thenReturn(mock(User.class));
        when(groupService.findGroupById("missingGroup")).thenReturn(null);

        Exception wrapper = assertThrows(RuntimeException.class, () -> {
            groupMessageService.postMessageIntoGroup(rawMessage).block();
        });

        Throwable actual = wrapper.getCause();

        assertNotNull(actual);
        assertInstanceOf(GroupNotFound.class, actual);
        assertEquals("User group not found with the provided ID : user123", actual.getMessage());

    }

    @Test
    void postMessageIntoGroup_userNotMappedToGroup_throwsBadRequestException() {
        RawGroupMessageDTO rawMessage = RawGroupMessageDTO.builder()
                .sentByUserId("user123")
                .sentToGroupId("group456")
                .messageText("Hello")
                .build();

        when(userService.findUserById("user123")).thenReturn(mock(User.class));
        when(groupService.findGroupById("group456")).thenReturn(mock(Group.class));
        when(groupService.findUserGroupMapping("user123", "group456")).thenReturn(null);

        Exception wrapper = assertThrows(RuntimeException.class, () -> {
            groupMessageService.postMessageIntoGroup(rawMessage).block();
        });

        Throwable actual = wrapper.getCause();

        assertNotNull(actual);
        assertInstanceOf(BadRequestException.class, actual);
        assertTrue(actual.getMessage().contains("User not mapped to the particular group"));
    }

    @Test
    void buildMessagePageResponseForTheGroups_returnsCorrectResponse() {
        Page<UserGroupMapping> mockPage = mock(Page.class);
        List<GroupChannelWithMessages> mockChannels = List.of(mock(GroupChannelWithMessages.class));

        when(mockPage.getTotalElements()).thenReturn(10L);
        when(mockPage.getTotalPages()).thenReturn(2);
        when(mockPage.getPageable()).thenReturn(PageRequest.of(1, 5));

        MessagePageResponse response = groupMessageService.buildMessagePageResponseForTheGroups(mockPage, mockChannels);

        assertNotNull(response);
        assertEquals(10L, response.getCount());
        assertEquals(2, response.getTotalPages());
        assertEquals(1, response.getPage());
    }

    @Test
    void buildGroupMessageMetaDataFromMessagePage_returnsMetaData() {
        GroupMessage mockMessage = mock(GroupMessage.class);
        Page<GroupMessage> mockPage = mock(Page.class);

        when(mockPage.getContent()).thenReturn(List.of(mockMessage));
        when(mockPage.getTotalElements()).thenReturn(5L);
        when(mockPage.getTotalPages()).thenReturn(1);

        GroupMessagesMetaData metaData = groupMessageService.buildGroupMessageMetaDataFromMessagePage(mockPage);

        assertNotNull(metaData);
        assertEquals(5L, metaData.getCount());
        assertEquals(1, metaData.getTotalPages());
        assertEquals(1, metaData.getMessages().size());
    }

    @Test
    void mapGroupMessageToUpdatedGroupMessage_validMessage_mapsCorrectly() {
        CommonMessageData commonData = CommonMessageData.builder()
                .sentBy("Alice")
                .sentById("user123")
                .sentOn(LocalDateTime.now())
                .repliedToMessageId("msg789")
                .build();

        GroupMessage groupMessage = GroupMessage.builder()
                .uniqueId("msg123")
                .groupId("group456")
                .groupName("Test Group")
                .commonMessageData(commonData)
                .build();

        UpdatedGroupMessageResponse response = groupMessageService.mapGroupMessageToUpdatedGroupMessage(groupMessage);

        assertNotNull(response);
        assertEquals("msg123", response.getUniqueId());
        assertEquals("Alice", response.getSentBy());
        assertEquals("user123", response.getSentById());
        assertEquals("group456", response.getGroupId());
        assertEquals("Test Group", response.getGroupName());
        assertEquals("msg789", response.getRepliedToMessageId());
    }

    @Test
    void saveMessageIntoDb_savesAndReturnsMessage() {
        GroupMessage mockMessage = mock(GroupMessage.class);
        when(groupMessagesRepo.save(mockMessage)).thenReturn(mockMessage);

        GroupMessage result = groupMessageService.saveMessageIntoDb(mockMessage);

        assertNotNull(result);
        assertEquals(mockMessage, result);
    }

    @Test
    void buildGroupMessageFromRawGroupMessage_buildsCorrectMessage() {
        RawGroupMessageDTO raw = RawGroupMessageDTO.builder()
                .sentByUserId("user123")
                .sentToGroupId("group456")
                .messageText("Hello")
                .repliedToMessageId("msg789")
                .build();

        User user = User.builder().id("user123").name("Alice").build();
        Group group = Group.builder().id("group456").groupName("Test Group").build();

        when(uniqueDataBignumberService.getNextUniqueDateIdentifier(anyString(), anyString())).thenReturn(BigInteger.ONE);
        GroupMessage result = groupMessageService.buildGroupMessageFromRawGroupMessage(raw, user, group);

        assertNotNull(result);
        assertEquals("user123-1", result.getUniqueId());
        assertEquals("user123", result.getCommonMessageData().getSentById());
        assertEquals("Alice", result.getCommonMessageData().getSentBy());
        assertEquals("Hello", result.getCommonMessageData().getMessage());
        assertEquals("msg789", result.getCommonMessageData().getRepliedToMessageId());
        assertEquals("group456", result.getGroupId());
        assertEquals("Test Group", result.getGroupName());
    }


}
