package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/new")
    private ResponseEntity<String> saveMessage(@RequestBody(required = true) MessageModelMapper messageModel){
        return new ResponseEntity<String>(messageService.saveMessage(messageModel), HttpStatus.ACCEPTED);
    }

}
