package com.iteamoa.message.dto;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.entity.MessageEntity;
import com.iteamoa.message.utils.KeyConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String pk;
    private String sk;
    private DynamoDbEntityType entityType;
    private String creatorId;
    private Boolean userStatus;
    private String recipientId;
    private String messageContent;
    private Boolean messageStatus;
    private LocalDateTime timestamp;

    public static MessageDto toMessageDto(MessageEntity messageEntity) {
        return new MessageDto(
                KeyConverter.toStringId(messageEntity.getPk()),
                KeyConverter.toStringId(messageEntity.getSk()),
                messageEntity.getEntityType(),
                KeyConverter.toStringId(messageEntity.getCreatorId()),
                messageEntity.getUserStatus(),
                KeyConverter.toStringId(messageEntity.getRecipientId()),
                messageEntity.getMessageContent(),
                messageEntity.getMessageStatus(),
                messageEntity.getTimestamp()
        );
    }

}
