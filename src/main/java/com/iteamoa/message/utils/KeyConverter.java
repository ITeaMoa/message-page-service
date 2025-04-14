package com.iteamoa.message.utils;

import com.iteamoa.message.constant.DynamoDbEntityType;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.LocalDateTime;

public class KeyConverter {
    static final String delimiter = "#";

    public static String toPk(DynamoDbEntityType type, String id){
        return type.getType() + delimiter + id;
    }

    public static String toPk(DynamoDbEntityType type, LocalDateTime localDateTime){
        return type.getType() + delimiter + localDateTime;
    }

    public static String toStringId(String key) {return key.split(delimiter)[1];}

    public static Key toKey(String Pk, String Sk){
        return Key.builder()
                .partitionValue(Pk)
                .sortValue(Sk)
                .build();
    }

    public static String toSeperatedId(String key){
        return key.split(delimiter)[1];
    }

}
