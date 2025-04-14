package com.iteamoa.message.repository;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.entity.MessageEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class MessageRepository {
    private final DynamoDbTable<MessageEntity> messageTable;

    public MessageRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.messageTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(MessageEntity.class));
    }

    public void saveMessage(MessageDto messageDto) {
        messageTable.putItem(new MessageEntity(messageDto));
    }

    public void deleteMessage(MessageDto messageDto) {
        messageTable.deleteItem(new MessageEntity(messageDto));
    }
}
