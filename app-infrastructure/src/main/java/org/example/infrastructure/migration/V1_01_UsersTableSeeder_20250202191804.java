package org.example.infrastructure.migration;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class V1_01_UsersTableSeeder_20250202191804 {

    private final DynamoDbClient dynamoDbClient;

    public V1_01_UsersTableSeeder_20250202191804(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void seedUsersTable() {
        String tableName = "Users";

        boolean tableExists = dynamoDbClient.listTables().tableNames().contains(tableName);
        if (!tableExists) {
            System.out.println("Table " + tableName + " has not been created yet. Skip adding data.");
            return;
        }

        insertUser("1", "Alice", "alice@example.com");
        insertUser("2", "Bob", "bob@example.com");
        insertUser("3", "Charlie", "charlie@example.com");
        insertUser("4", "Charlie", "charlie@example.com");
        insertUser("5", "Charlie", "charlie@example.com");
    }

    private void insertUser(String userId, String name, String email) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.builder().s(userId).build());
        item.put("name", AttributeValue.builder().s(name).build());
        item.put("email", AttributeValue.builder().s(email).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("Users")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        System.out.println("Add user: " + name);
    }
}
