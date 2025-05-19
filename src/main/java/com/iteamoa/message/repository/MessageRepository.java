package com.iteamoa.message.repository;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.entity.MessageEntity;
import com.iteamoa.message.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<MessageEntity> getAllMessage(String messageId, String userId) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.MESSAGE, messageId))
        );

        final SdkIterable<Page<MessageEntity>> pagedResult = messageTable.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(true)
                .attributesToProject());

        List<MessageEntity> messageList = new ArrayList<>();
        boolean foundTrueStatus = false;

        for (Page<MessageEntity> page : pagedResult) {
            for (MessageEntity entity : page.items()) {
                if (!foundTrueStatus && Objects.equals(entity.getRecipientId(), KeyConverter.toPk(DynamoDbEntityType.USER, userId))) {
                    if (Boolean.TRUE.equals(entity.getMessageStatus())) {
                        foundTrueStatus = true;
                    } else {
                        entity.setMessageStatus(true);
                        messageTable.updateItem(entity);
                    }
                }
                messageList.add(entity);
            }
        }

        return messageList;
    }
}
