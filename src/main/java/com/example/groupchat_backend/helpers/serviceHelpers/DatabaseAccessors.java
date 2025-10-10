package com.example.groupchat_backend.helpers.serviceHelpers;

import com.example.groupchat_backend.models.group.Group;
import com.example.groupchat_backend.models.group.UserGroupMapping;
import com.example.groupchat_backend.models.message.GroupMessage;
import com.example.groupchat_backend.models.user.User;
import com.example.groupchat_backend.repository.GroupMessagesRepo;
import com.example.groupchat_backend.repository.GroupRepo;
import com.example.groupchat_backend.repository.GroupUserRepo;
import com.example.groupchat_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class DatabaseAccessors {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public GroupRepo groupRepo;

    @Autowired
    private GroupUserRepo groupUserRepo;

    @Autowired
    private GroupMessagesRepo groupMessagesRepo;

    public Group findGroupByGroupId(String groupId){
        return groupRepo.findById(groupId).orElse(null);
    }

    public User findUserByUserId(String userId){
        return userRepo.findById(userId).orElse(null);
    }

    public UserGroupMapping findUserGroupMapping(String userId, String groupId){
        return groupUserRepo.findByUserIdAndGroupId(userId, groupId).orElse(null);
    }

    public Page<UserGroupMapping> getAllGroupsForUserWithPage(String userId, Pageable pageable){
        return groupUserRepo.findByUserId(userId, pageable);
    }

    public Optional<Group> getGroupWithGroupId(String groupId){
        return groupRepo.findById(groupId);
    }

    public Optional<UserGroupMapping> getUserGroupMapping(String userId, String groupId){
        return groupUserRepo.findByUserIdAndGroupId(userId, groupId);
    }

    public Page<GroupMessage> getAllMessageForGroupId(String groupId, Pageable pageable){
        return groupMessagesRepo.findAllByGroupId(groupId, pageable);
    }
    public GroupMessage saveMessageToGroup(GroupMessage groupMessage){
        return groupMessagesRepo.save(groupMessage);
    }
    public Page<GroupMessage> getAllGroupMessagesByPage(Group group, Pageable pageable){
        return groupMessagesRepo.findAllByGroupId(group.getId(), pageable);
    }

}
