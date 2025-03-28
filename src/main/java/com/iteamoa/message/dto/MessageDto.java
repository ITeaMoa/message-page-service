package com.iteamoa.message.dto;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.entity.MessageEntity;
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
    private String receiverId;
    private String messageContent;
    private boolean messageStatus;
    private LocalDateTime timestamp;

    public static MessageDto toMessageDto(MessageEntity messageEntity) {
        return new MessageDto(
                messageEntity.getPk(),
                messageEntity.getSk(),
                messageEntity.getEntityType(),
                messageEntity.getCreatorId(),
                messageEntity.getUserStatus(),
                messageEntity.getReceiverId(),
                messageEntity.getMessageContent(),
                messageEntity.getMessageStatus(),
                messageEntity.getTimestamp()
        );
    }

}
