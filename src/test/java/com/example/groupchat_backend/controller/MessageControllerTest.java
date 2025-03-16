package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.DataMappers.MessageUpdateBody;
import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MessageController.class)
@AutoConfigureMockMvc(addFilters = false) //disabling the security
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    void getAllMessages_ResponseSuccess() throws Exception {
        List<Message> messages = Arrays.asList(
                Message.builder().uniqueId("1234").build()
        );

        when(messageService.getAllMessages(any())).thenReturn(messages);

        mockMvc.perform(get("/message/all")
                        .param("groupId", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1)) // Corrected "lenagth" to "length"
                .andExpect(jsonPath("$[0].uniqueId").value(messages.get(0).getUniqueId()));
    }

    @Test
    void getAllMessages_RespondeWithNoContent() throws  Exception{
        when(messageService.getAllMessages(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/message/all")
                .param("groupId", "1234"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllMessages_ResponseWithIncompleteRequest() throws Exception{
        mockMvc.perform(get("/message/all"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveMessage_ResponseOkWithCreated()throws Exception{
        String requestBody = "{\"message\" : \"test\", \"sentBy\" : \"test\", \"sentById\" : \"usr1234\", \"groupId\" : \"grp1234\"}";

        when(messageService.saveMessage(any(MessageModelMapper.class))).thenReturn(CommonAppData.SUCCESSFUL_MESSAGE_SAVE);

        mockMvc.perform(post("/message/new")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated());
    }

    @Test
    void saveMessage_ResponseWithInvalidRequestMissingData() throws Exception{
        String requestBody = "{\"message\" : \"test\", \"sentById\" : \"usr1234\", \"groupId\" : \"grp1234\"}";
        mockMvc.perform(post("/message/new")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateMessage_ResponseWithSuccess() throws Exception{
        String requestBody = "{\"uniqueId\" : \"test\", \"message\" : \"sample\"}";

        when(messageService.updateMessage(any(MessageUpdateBody.class))).thenReturn(CommonAppData.SUCCESSFUL_MESSAGE_UPDATE);

        mockMvc.perform(put("/message/update")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void updateMessage_ResponseWithInvalidRequest() throws Exception{
        String requestBody = "{\"uniqueId\" : \"test\"";

        when(messageService.updateMessage(any(MessageUpdateBody.class))).thenReturn(CommonAppData.SUCCESSFUL_MESSAGE_UPDATE);

        mockMvc.perform(put("/message/update")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}
