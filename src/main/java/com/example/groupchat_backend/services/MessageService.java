package com.example.groupchat_backend.services;
import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.DataMappers.MessageUpdateBody;
import com.example.groupchat_backend.DataNotFoundException;
import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.repository.MessagesRepo;
import com.example.groupchat_backend.util.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessagesRepo messagesRepo;

    @Autowired
    private UniqueDataBignumberService uniqueDataBignumberService;

    public String saveMessage(MessageModelMapper messageModel){
        String messageData = messageModel.getMessage();
        LocalDateTime currentLocalDateTime = Util.getCurrentDateTime();
        String sentOnString = Util.getLocalDateToStringDate(currentLocalDateTime);

        BigInteger nextBigIntegerCounter = uniqueDataBignumberService.getNextUniqueDateIdentifier(sentOnString);
        Message message = Message.builder().
                message(messageData).
                sentOn(currentLocalDateTime).
                groupId(messageModel.getGroupId()).
                sentById(messageModel.getSentById()).
                sentBy(messageModel.getSentBy()).
                uniqueId(Util.getUniqueMessageId(sentOnString, nextBigIntegerCounter)).build();

        messagesRepo.save(message);
        return CommonAppData.SUCCESSFUL_MESSAGE_SAVE;
    }

    public List<Message> getAllMessages(String groupId){
        return messagesRepo.findAllByGroupId(groupId);
    }

    @SneakyThrows
    public String updateMessage(MessageUpdateBody messageUpdateBody){
        Message foundMessage = messagesRepo.findByUniqueId(messageUpdateBody.getUniqueId());

        if(foundMessage == null)throw new DataNotFoundException("Data not found with message id sent");

        foundMessage.setMessage(messageUpdateBody.getMessage());
        messagesRepo.save(foundMessage);
        return CommonAppData.SUCCESSFUL_MESSAGE_UPDATE;
    }

}
