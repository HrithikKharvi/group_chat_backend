package com.example.groupchat_backend.services.interfaces;

import com.example.groupchat_backend.models.message.GroupMessagesMetaData;
import com.example.groupchat_backend.models.message.RawGroupMessageDTO;
import com.example.groupchat_backend.models.message.UpdatedGroupMessageResponse;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.models.message.baseClasses.MessagesMetaData;
import reactor.core.publisher.Mono;

public interface MessageService {
    public Mono<MessagesMetaData> getMessagesForGroupForPage(String groupId, int pageSize, int nextPage);
    public Mono<MessagePageResponse> getChannelsWithMessages(String userId, Integer currentPage, int count);
    public Mono<UpdatedGroupMessageResponse> postMessageIntoGroup(RawGroupMessageDTO userSentRawMessage);
}
