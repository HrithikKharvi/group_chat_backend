package com.example.groupchat_backend.services;
import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.repository.MessagesRepo;
import com.example.groupchat_backend.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;

@Service
public class MessageService {

    @Autowired
    private MessagesRepo messagesRepo;

    @Autowired
    private UniqueDataBignumberService uniqueDataBignumberService;

    public String saveMessage(MessageModelMapper messageModel){
        String messageData = messageModel.getMessage();
        LocalDate currentLocalDate = Util.getCurrentDate();
        String sentOnString = Util.getLocalDateToStringDate(currentLocalDate);

        BigInteger nextBigIntegerCounter = uniqueDataBignumberService.getNextUniqueDateIdentifier(sentOnString);
        Message message = Message.builder().
                message(messageData).
                timeStamp(currentLocalDate).
                uniqueId(Util.getUniqueMessageId(sentOnString, nextBigIntegerCounter)).build();

        messagesRepo.save(message);
        return CommonAppData.SUCCESSFUL_MESSAGE_SAVE;
    }

}
