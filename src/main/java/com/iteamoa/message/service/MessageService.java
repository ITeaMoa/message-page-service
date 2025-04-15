package com.iteamoa.message.service;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.entity.MessageEntity;
import com.iteamoa.message.entity.UserProfileEntity;
import com.iteamoa.message.repository.MessageRepository;
import com.iteamoa.message.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserProfileRepository userProfileRepository;

    public void saveMessage(MessageDto messageDto) {
        Objects.requireNonNull(messageDto.getCreatorId(), "CreatorId cannot be null");
        Objects.requireNonNull(messageDto.getReceiverId(), "ReceiverId cannot be null");
        Objects.requireNonNull(messageDto.getMessageContent(), "Content cannot be null");

        UserProfileEntity sender = userProfileRepository.getUserProfileByUserId(messageDto.getCreatorId());
        UserProfileEntity recipient = userProfileRepository.getUserProfileByUserId(messageDto.getReceiverId());
        if(messageDto.getPk() == null) {
            messageDto.setPk(sender.getMessageId().get(messageDto.getReceiverId()));
            if(messageDto.getPk() == null) {
                messageDto.setPk(UUID.randomUUID().toString());
                sender.getMessageId().put(messageDto.getReceiverId(), messageDto.getPk());
                recipient.getMessageId().put(messageDto.getCreatorId(), messageDto.getPk());
                userProfileRepository.updateUserProfile(sender);
                userProfileRepository.updateUserProfile(recipient);
            }
        }

        messageRepository.saveMessage(messageDto);
    }

    public void deleteMessage(MessageDto messageDto) {
        Objects.requireNonNull(messageDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(messageDto.getSk(), "Sk cannot be null");

        messageRepository.deleteMessage(messageDto);
    }

    public List<MessageDto> getAllMessage(String messageId) {
        List<MessageEntity> messageEntities = messageRepository.getAllMessage(messageId);
        return messageEntities.stream()
                .map(MessageDto::toMessageDto)
                .collect(Collectors.toList());
    }

    public Map<String, String> getUserList(String pk) {
        Objects.requireNonNull(pk, "Pk cannot be null");

        var userProfile = userProfileRepository.getUserProfileByUserId(pk);
        var messageMap = userProfile.getMessageId();

        return messageMap.keySet().stream()
                .collect(Collectors.toMap(
                        key -> userProfileRepository.getUserProfileByUserId(key).getNickname(),
                        Function.identity()
                ));
    }
}
