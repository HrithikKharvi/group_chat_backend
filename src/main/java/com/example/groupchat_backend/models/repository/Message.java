package com.example.groupchat_backend.models.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @Column(name="unique_id")
    private String uniqueId;

    @Column(name="message")
    private String message;

    @Column(name="sent_on")
    @JsonFormat(pattern="YYYY-MM-DD mm:hh")
    private LocalDateTime sentOn;

    @Column(name="sent_by")
    private String sentBy;

    @Column(name="sent_by_id")
    private String sentById;

    @Column(name="group_id")
    private String groupId;

}
