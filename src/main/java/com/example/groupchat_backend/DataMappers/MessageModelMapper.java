package com.example.groupchat_backend.DataMappers;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
@Builder
public class MessageModelMapper {
    @NonNull
    private String message;
    @NonNull
    private String sentBy;
    @NonNull
    private String sentById;
    @NonNull
    private String groupId;
}
