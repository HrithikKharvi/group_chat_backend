package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupChannelWithMessages;
import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.repository.GroupRepo;
import com.example.groupchat_backend.repository.GroupUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupUserRepo groupUserRepo;

    @Mock
    private GroupRepo groupRepo;

    @Spy
    @InjectMocks
    private GroupService groupService;

    @Test
    void getAllGroupsForUserWithPage_returnsGroupsForUser(){
        Page<UserGroupMapping> page = mock(Page.class);

        when(groupUserRepo.findByUserId(anyString(), any(Pageable.class))).thenReturn(page);

        Page<UserGroupMapping> actualPage = groupService.getAllGroupsForUserWithPage("test", mock(Pageable.class)).block();

        assertTrue(actualPage != null);
    }

    @Test
    void getAllGroupsForUserWithPage_returnsNoGroupsForUser(){
        when(groupUserRepo.findByUserId(anyString(), any(Pageable.class))).thenReturn(null);
        Page<UserGroupMapping> actualPage = groupService.getAllGroupsForUserWithPage("test", mock(Pageable.class)).block();

        assertTrue(actualPage == null);
    }

    @Test
    void buildGroupChannelsWithMessagesFromGroups_returnGroupChannelsWithMessages(){
        UserGroupMapping userGroupMapping = UserGroupMapping.builder().build();
        GroupMessagesMetaData groupMessagesMetaData = GroupMessagesMetaData.builder().build();

        GroupChannelWithMessages groupChannelWithMessages = groupService.buildGroupChannelsWithMessagesFromGroups(userGroupMapping, groupMessagesMetaData);

        assertThat(groupChannelWithMessages).isNotNull();
        verify(groupService).buildGroupChannelsWithMessagesFromGroups(any(), any());
    }

    @Test
    void getAllGroupsById_returnsGroupsForUser(){
        Group group = Group.builder().build();
        when(groupRepo.findById(anyString())).thenReturn(Optional.of(group));
        Group actualPage = groupService.findGroupById("test");

        assertTrue(actualPage != null);
    }

    @Test
    void getAllGroupsById_returnsNoGroupsForUser(){
        when(groupRepo.findById(anyString())).thenReturn(Optional.empty());
        Group actualPage = groupService.findGroupById("test");

        assertTrue(actualPage == null);
    }


    @Test
    void findUserGroupMapping_returnsUserGroupMapping(){
        UserGroupMapping userGroupMapping = UserGroupMapping.builder().build();
        when(groupUserRepo.findByUserIdAndGroupId(anyString(), anyString())).thenReturn(Optional.of(userGroupMapping));
        UserGroupMapping actualUserGroupMapping = groupService.findUserGroupMapping("testUser", "testGroupId");

        assertTrue(actualUserGroupMapping != null);
    }

    @Test
    void findUserGroupMapping_returnsNoUserGroupMapping(){
        when(groupUserRepo.findByUserIdAndGroupId(anyString(), anyString())).thenReturn(Optional.empty());
        UserGroupMapping actualUserGroupMapping = groupService.findUserGroupMapping("testUser", "testGroupId");

        assertTrue(actualUserGroupMapping == null);
    }

}
