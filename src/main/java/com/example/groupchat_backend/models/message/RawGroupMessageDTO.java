package com.example.groupchat_backend.models.message;

import com.example.groupchat_backend.models.message.baseClasses.RawMessageDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class RawGroupMessageDTO extends RawMessageDTO {
    public RawGroupMessageDTO(){
        super();
    }
}
