package com.iteamoa.message.entity;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;
import java.util.Map;

@Setter
@DynamoDbBean
public class UserProfileEntity extends BaseEntity {
    private String nickname;
    private String avatarUrl;
    private String headLine;
    private List<String> tags;
    private List<String> educations;
    private List<String> personalUrl;
    private List<String> experiences;
    private String email;
    private Map<String, String> messageId;

    public UserProfileEntity() {}

    @DynamoDbAttribute("nickname")
    public String getNickname() {
        return nickname;
    }

    @DynamoDbAttribute("avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @DynamoDbAttribute("headLine")
    public String getHeadLine() {
        return headLine;
    }

    @DynamoDbAttribute("tags")
    public List<String> getTags() {
        return tags;
    }

    @DynamoDbAttribute("educations")
    public List<String> getEducations() {
        return educations;
    }

    @DynamoDbAttribute("personalUrl")
    public List<String> getPersonalUrl() {
        return personalUrl;
    }

    @DynamoDbAttribute("experiences")
    public List<String> getExperiences() {
        return experiences;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    @DynamoDbAttribute("messageId")
    public Map<String, String> getMessageId() {
        return messageId;
    }

}
