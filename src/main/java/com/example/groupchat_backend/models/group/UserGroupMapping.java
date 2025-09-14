package com.example.groupchat_backend.models.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user_group_mapping")
public class UserGroupMapping {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="group_id")
    private String groupId;

    @Column(name="user_id")
    private String userId;

    @Column(name="joined_on")
    private LocalDateTime joinedOn;

    @Column(name="group_name")
    private String groupName;

    @Override
    public String toString() {
        return "UserGroupMapping{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userId='" + userId + '\'' +
                ", joinedOn=" + joinedOn +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
