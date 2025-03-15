package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.DataMappers.MessageModelMapper;
import com.example.groupchat_backend.models.Message;
import com.example.groupchat_backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Messages API", description = "controller to communicate with the messages")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/all")
    @Operation(description="Fetch all the messages for the group based on the group ID provided")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "All the messages retrieved successfully",  content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Message.class)))),
            @ApiResponse(responseCode="500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode="204", description = "No message found!")
    })
    private ResponseEntity<List<Message>> getAllMessages(
            @RequestParam(name="groupId", required = true) String groupId
    ){
        List<Message> messages = messageService.getAllMessages(groupId);
        return messages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(messages);
    }

    @Operation(description = "Save sent message")
    @PostMapping("/new")
    @CrossOrigin
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "message saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request. Please validate the body before making the request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    private ResponseEntity<String> saveMessage(
            @RequestBody(required = true) MessageModelMapper messageModel){
        return new ResponseEntity<String>(messageService.saveMessage(messageModel), HttpStatus.ACCEPTED);
    }

}
