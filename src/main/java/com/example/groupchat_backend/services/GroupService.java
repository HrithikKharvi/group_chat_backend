package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.GroupResponse;
import com.example.groupchat_backend.models.repository.Group;
import com.example.groupchat_backend.repository.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepo groupRepo;

    //method to list out all the groups saved in the database
    public List<GroupResponse> getAllGroups(){
        return groupRepo.findAll().stream()
                .map(grp -> GroupResponse.builder()
                        .groupId(grp.getId())
                        .groupName(grp.getGroupName())
                        .createdOn(grp.getCreatedOn())
                        .build())
                .distinct().toList();
    }

}
