package com.example.groupchat_backend.models.message.baseClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class MessagesMetaData <T>{

    private long count;
    private int page;
    private int totalPages;
    private List<T> messages;
}
