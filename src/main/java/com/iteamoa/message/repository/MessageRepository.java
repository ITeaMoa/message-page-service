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

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MessageEntity> getAllMessage(String messageId) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.MESSAGE, messageId))
        );

        final SdkIterable<Page<MessageEntity>> pagedResult = messageTable.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(true)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }
}
