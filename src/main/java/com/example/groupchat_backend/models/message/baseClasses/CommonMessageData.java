package com.example.groupchat_backend.models.message.baseClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommonMessageData {
    @Column(name="message_text")
    private String message;

    @Column(name="sent_on")
    @JsonFormat(pattern="YYYY-MM-DD mm:hh")
    private LocalDateTime sentOn;

    @Column(name="sent_by")
    private String sentBy;

    @Column(name="sent_by_id")
    private String sentById;

    //null when the message is not replied to any message
    @Column(name="repliedTo")
    private String repliedToMessageId;
}
