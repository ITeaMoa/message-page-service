package com.iteamoa.message.entity;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;

@Setter
@DynamoDbBean
public class MessageEntity extends BaseEntity {
    private String recipientId;
    private String messageContent;
    private boolean messageStatus;

    public MessageEntity() {}
    public MessageEntity(MessageDto messageDto) {
        super(
            messageDto.getPk(),
            messageDto.getSk() == null ? LocalDateTime.now().toString() : messageDto.getSk(),
            KeyConverter.toPk(DynamoDbEntityType.USER, messageDto.getCreatorId()),
            DynamoDbEntityType.MESSAGE
        );
        this.recipientId = KeyConverter.toPk(DynamoDbEntityType.USER, messageDto.getRecipientId());
        this.messageContent = messageDto.getMessageContent();
        this.messageStatus = false;
    }

    @DynamoDbAttribute("recipientId")
    public String getRecipientId() {
        return recipientId;
    }

    @DynamoDbAttribute("messageContent")
    public String getMessageContent() {
        return messageContent;
    }

    @DynamoDbAttribute("messageStatus")
    public boolean getMessageStatus() {
        return messageStatus;
    }

}
