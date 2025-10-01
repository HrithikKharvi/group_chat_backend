package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Tag(name="Group API", description="This api controller is dedicated for group related query")
@RequestMapping("/group")
@CrossOrigin("http://localhost:3000")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/info")
    @Operation(description = "Get the information about the group")
    @CrossOrigin
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the information about the group based on the userid and groupid"),
            @ApiResponse(responseCode = "404", description = "Group information not found for the group id"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Mono<ResponseEntity<Group>> getGroupInfoByGroupId(@RequestParam String groupId,
                                                             @RequestParam String userId){

        return groupService.validateAndGetGroupInfo(userId, groupId)
                .map(ResponseEntity::ok);
    }

}
