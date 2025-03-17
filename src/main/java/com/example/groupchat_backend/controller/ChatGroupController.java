package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.GroupResponse;
import com.example.groupchat_backend.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name ="Group API", description = "Operations on groups of the chat")
@RequestMapping("/group")
public class ChatGroupController {

    @Autowired
    private GroupService groupService;

    @Operation(description="Fetch all the group list")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Fetched all the groups successfully"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<GroupResponse>> getAllGroups(){
        List<GroupResponse> groups = groupService.getAllGroups();
        return groups.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(groups);
    }

}
