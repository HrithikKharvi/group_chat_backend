package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.message.baseClasses.Message;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.services.GroupMessageServiceImpl;
import com.example.groupchat_backend.services.interfaces.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Tag(name="Messages API", description = "controller to communicate with the messages")
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService groupMessageService;

    @GetMapping("/all")
    @Operation(description="Fetch all the messages for the group based on the group ID provided")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "All the messages retrieved successfully",  content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Message.class)))),
            @ApiResponse(responseCode="500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode="204", description = "No message found!")
    })
    public Mono<ResponseEntity<MessagePageResponse>> getAllMessages(
            @RequestParam(name="userId", required = true) String userId,
            @RequestParam(name="pageSize", required = false, defaultValue = "10")int pageSize,
            @RequestParam(name="page", required = false, defaultValue = "0") int page
    ){
        return groupMessageService.getChannelsWithMessages(userId, page, pageSize).map(ResponseEntity::ok);
    }

}
