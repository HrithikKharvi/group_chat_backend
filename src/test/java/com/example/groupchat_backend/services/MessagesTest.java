package com.example.groupchat_backend.services;
import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.DataMappers.MessageUpdateBody;
import com.example.groupchat_backend.DataNotFoundException;
import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.repository.MessagesRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class MessagesTest {

    @Mock
    private MessagesRepo messagesRepo;
    @Mock
    private UniqueDataBignumberService uniqueDataBignumberService;
    @InjectMocks
    private MessageService messageService;

    @Test
    public void testUpdateMessageSuccess(){
        MessageUpdateBody messageUpdateBody = MessageUpdateBody.builder()
                .message("Change it!")
                .uniqueId("test1234")
                .build();

        Message message = Message.builder()
                .uniqueId("test1234")
                .build();

        when(messagesRepo.findByUniqueId(anyString())).thenReturn(message);
        when(messagesRepo.save(any(Message.class))).thenReturn(null);

        messageService.updateMessage(messageUpdateBody);

        verify(messagesRepo).save(any(Message.class));
    }

    @Test
    void testUpdateMessageDataNotFound(){
        when(messagesRepo.findByUniqueId(anyString())).thenReturn(null);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->{
            messageService.updateMessage(MessageUpdateBody.builder().uniqueId("test").message("test").build());
        });

        assertEquals("Data not found with message id sent", exception.getMessage());

    }

    @Test
    public void testSaveMessageSuccess(){
        MessageModelMapper messageModelMapper = MessageModelMapper.builder().message("test")
                .groupId("grp1234")
                .sentBy("test")
                .sentById("usr123")
                .build();
        when(uniqueDataBignumberService.getNextUniqueDateIdentifier(any()))
                .thenReturn(BigInteger.ONE);
        when(messagesRepo.save(any())).thenReturn(null);

        String responseMessage = messageService.saveMessage(messageModelMapper);

        Assertions.assertEquals(responseMessage, CommonAppData.SUCCESSFUL_MESSAGE_SAVE);
        verify(messagesRepo).save(any());
    }

    @Test
    public void testGetAllMessagesSuccess(){
        List<Message> messages  = new ArrayList<>();

        Message message = Message.builder().uniqueId("1212202323").message("Hey").build();
        messages.add(message);

        when(messagesRepo.findAllByGroupId(anyString())).thenReturn(messages);

        List<Message> returnedMessages = messageService.getAllMessages(anyString());

        verify(messagesRepo).findAllByGroupId(anyString());
        Assertions.assertEquals(1, returnedMessages.size());
    }

}
