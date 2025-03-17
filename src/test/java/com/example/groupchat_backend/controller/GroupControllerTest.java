package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.GroupResponse;
import com.example.groupchat_backend.services.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChatGroupController.class)
@AutoConfigureMockMvc(addFilters = false) //disabling the security
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Test
    void getAllGroups_RespondWithAllGroupsSuccess() throws Exception{
        List<GroupResponse> groups = Arrays.asList(
                GroupResponse.builder().groupId("grp1234").build()
        );

        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(get("/group/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(groups.size()));
    }

    @Test
    void getAllGroups_ResponseWithNoContent() throws Exception{
        when(groupService.getAllGroups()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/group/all"))
                .andExpect(status().isNoContent());

    }


}
