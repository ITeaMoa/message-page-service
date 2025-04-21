package com.iteamoa.message.repository;

import com.iteamoa.message.constant.DynamoDbEntityType;
import com.iteamoa.message.entity.UserProfileEntity;
import com.iteamoa.message.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class UserProfileRepository {
    private final DynamoDbTable<UserProfileEntity> userProfileTable;

    public UserProfileRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.userProfileTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(UserProfileEntity.class));
    }

    public UserProfileEntity getUserProfileByUserId(String pk) {
        return userProfileTable.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.USER, pk),
                KeyConverter.toPk(DynamoDbEntityType.PROFILE, "")
        ));
    }

    public void updateUserProfile(UserProfileEntity userProfileEntity) {
        userProfileTable.updateItem(userProfileEntity);
    }

}
