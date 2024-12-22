package com.example.groupchat_backend.services;
import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.models.UniqueDateIdentifier;
import com.example.groupchat_backend.repository.MessagesRepo;
import com.example.groupchat_backend.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessagesTest {

    @Mock
    private MessagesRepo messagesRepo;
    @Mock
    private UniqueDataBignumberService uniqueDataBignumberService;
    @InjectMocks
    private MessageService messageService;

    @Test
    public void testSaveMessageSuccess(){
        MessageModelMapper messageModelMapper = MessageModelMapper.builder().message("test").sentDate(Util.getCurrentDate()).build();
        when(uniqueDataBignumberService.getNextUniqueDateIdentifier(any()))
                .thenReturn(BigInteger.ONE);
        when(messagesRepo.save(any())).thenReturn(null);

        String responseMessage = messageService.saveMessage(messageModelMapper);

        Assertions.assertEquals(responseMessage, CommonAppData.SUCCESSFUL_MESSAGE_SAVE);
        verify(messagesRepo).save(any());
    }
}
