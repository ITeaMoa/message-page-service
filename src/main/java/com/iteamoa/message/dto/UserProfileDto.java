package com.iteamoa.message.dto;

import com.iteamoa.message.entity.UserProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String pk;
    private String sk;
    private String entityType;
    private String nickname;
    private String avatarUrl;
    private String headLine;
    private List<String> tags;
    private List<String> educations;
    private List<String> personalUrl;
    private List<String> experiences;
    private String email;
    private LocalDateTime timestamp;
    private String creatorId;
    private Map<String, String> messageId;

    public static UserProfileDto toUserProfileDto(UserProfileEntity userProfileEntity) {
        return new UserProfileDto(
                userProfileEntity.getPk(),
                userProfileEntity.getSk(),
                userProfileEntity.getEntityType().getType(),
                userProfileEntity.getNickname(),
                userProfileEntity.getAvatarUrl(),
                userProfileEntity.getHeadLine(),
                userProfileEntity.getTags(),
                userProfileEntity.getEducations(),
                userProfileEntity.getPersonalUrl(),
                userProfileEntity.getExperiences(),
                userProfileEntity.getEmail(),
                userProfileEntity.getTimestamp(),
                userProfileEntity.getCreatorId(),
                userProfileEntity.getMessageId()
        );
    }
}
