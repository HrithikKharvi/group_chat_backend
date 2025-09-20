package com.example.groupchat_backend.services.interfaces;

import com.example.groupchat_backend.models.message.RawGroupMessageDTO;
import com.example.groupchat_backend.models.message.UpdatedGroupMessageResponse;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import reactor.core.publisher.Mono;

public interface MessageService {
    public Mono<MessagePageResponse> getChannelsWithMessages(String userId, Integer currentPage, int count);
    public Mono<UpdatedGroupMessageResponse> postMessageIntoGroup(RawGroupMessageDTO userSentRawMessage);
}
