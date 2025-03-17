package com.example.groupchat_backend.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MessageUpdateBody {

    @NonNull
    private String uniqueId;
    @NonNull
    private String message;

}
