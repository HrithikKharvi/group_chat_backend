package com.example.groupchat_backend.helpers.serviceHelpers;

import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.GroupRepo;
import com.example.groupchat_backend.repository.GroupUserRepo;
import com.example.groupchat_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAccessors {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public GroupRepo groupRepo;

    @Autowired
    private GroupUserRepo groupUserRepo;

    public Group findGroupByGroupId(String groupId){
        return groupRepo.findById(groupId).orElse(null);
    }

    public User findUserByUserId(String userId){
        return userRepo.findById(userId).orElse(null);
    }

    public UserGroupMapping findUserGroupMapping(String userId, String groupId){
        return groupUserRepo.findByUserIdAndGroupId(userId, groupId).orElse(null);
    }

}
