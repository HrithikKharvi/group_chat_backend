package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.GroupResponse;
import com.example.groupchat_backend.models.repository.Group;
import com.example.groupchat_backend.repository.GroupRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ChatGroupService {

    @Mock
    private GroupRepo groupRepo;

    @InjectMocks
    private GroupService groupService;

    @Test
    void getAllGroups_ReturnWithListOfService(){
        List<Group> groups = Arrays.asList(
                Group.builder().groupName("test").build()
        );

        when(groupRepo.findAll()).thenReturn(groups);

        List<GroupResponse> allResponse = groupService.getAllGroups();

        assertThat(allResponse.size()).isEqualTo(groups.size());
        assertThat(allResponse.get(0).getGroupName()).isEqualTo(groups.get(0).getGroupName());

    }
}
