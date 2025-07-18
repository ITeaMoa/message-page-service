package com.iteamoa.message.service;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.entity.MessageEntity;
import com.iteamoa.message.entity.UserProfileEntity;
import com.iteamoa.message.repository.MessageRepository;
import com.iteamoa.message.repository.UserProfileRepository;
import com.iteamoa.message.utils.KeyConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void saveMessage(MessageDto messageDto) {
        Objects.requireNonNull(messageDto.getCreatorId(), "CreatorId cannot be null");
        Objects.requireNonNull(messageDto.getRecipientId(), "ReceiverId cannot be null");
        Objects.requireNonNull(messageDto.getMessageContent(), "Content cannot be null");

        UserProfileEntity sender = userProfileRepository.getUserProfileByUserId(messageDto.getCreatorId());
        UserProfileEntity recipient = userProfileRepository.getUserProfileByUserId(messageDto.getRecipientId());
        if(messageDto.getPk() == null) {
            messageDto.setPk(sender.getMessageId().get(messageDto.getRecipientId()));
            if(messageDto.getPk() == null) {
                messageDto.setPk(UUID.randomUUID().toString());
                sender.getMessageId().put(messageDto.getRecipientId(), messageDto.getPk());
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

    public List<MessageDto> getAllMessage(String userId, String recipientId) {
        UserProfileEntity userProfileEntity = userProfileRepository.getUserProfileByUserId(userId);
        String messageId = userProfileEntity.getMessageId().get(recipientId);

        List<MessageEntity> messageEntities = messageRepository.getAllMessage(messageId);
        List<MessageEntity> resultList = new ArrayList<>();
        boolean foundTrueStatus = false;

        for (MessageEntity entity : messageEntities) {
            if (!foundTrueStatus && Objects.equals(entity.getRecipientId(), KeyConverter.toPk(DynamoDbEntityType.USER, userId))) {
                if (Boolean.TRUE.equals(entity.getMessageStatus())) {
                    foundTrueStatus = true;
                } else {
                    entity.setMessageStatus(true);
                    messageRepository.updateMessageStatus(entity);
                }
            }
            resultList.add(entity);
        }

        return resultList.stream()
                .map(MessageDto::toMessageDto)
                .collect(Collectors.toList());
    }

    public Map<String, String> getUserList(String pk) {
        Objects.requireNonNull(pk, "Pk cannot be null");

        UserProfileEntity userProfile = userProfileRepository.getUserProfileByUserId(pk);
        Map<String, String> messageMap = userProfile.getMessageId();

        return messageMap.keySet().stream()
                .collect(Collectors.toMap(
                        key -> key,
                        key -> userProfileRepository.getUserProfileByUserId(key).getNickname()
                ));
    }

    public Map<String, String> getMessageCount(String pk) {
        Objects.requireNonNull(pk, "Pk cannot be null");

        UserProfileEntity userProfile = userProfileRepository.getUserProfileByUserId(pk);
        Map<String, String> messageMap = userProfile.getMessageId();

        Map<String, String> unreadCountMap = new HashMap<>();
        String myPk = KeyConverter.toPk(DynamoDbEntityType.USER, pk);

        for (Map.Entry<String, String> entry : messageMap.entrySet()) {
            String otherUserId = entry.getKey();
            String messageId = entry.getValue();

            List<MessageEntity> messages = messageRepository.getAllMessage(messageId);

            int unreadCount = 0;

            for (MessageEntity message : messages) {
                if (Objects.equals(message.getRecipientId(), myPk)) {
                    if (Boolean.FALSE.equals(message.getMessageStatus())) {
                        unreadCount++;
                    } else {
                        break;
                    }
                }
            }
            unreadCountMap.put(otherUserId, String.valueOf(unreadCount));
        }

        return unreadCountMap;
    }


}
