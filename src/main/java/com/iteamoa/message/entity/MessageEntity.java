package com.iteamoa.message.entity;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Setter
@DynamoDbBean
public class MessageEntity extends BaseEntity {
    private String receiverId;
    private String messageContent;
    private boolean messageStatus;

    public MessageEntity() {}
    public MessageEntity(MessageDto messageDto) {
        super(
            KeyConverter.toPk(DynamoDbEntityType.MESSAGE, messageDto.getPk()),
            KeyConverter.toPk(DynamoDbEntityType.USER, messageDto.getCreatorId()),
            DynamoDbEntityType.MESSAGE
        );
        this.receiverId = KeyConverter.toPk(DynamoDbEntityType.USER, messageDto.getReceiverId());
        this.messageContent = messageDto.getMessageContent();
        this.messageStatus = false;
    }

    @DynamoDbAttribute("receiverId")
    public String getReceiverId() {
        return receiverId;
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
