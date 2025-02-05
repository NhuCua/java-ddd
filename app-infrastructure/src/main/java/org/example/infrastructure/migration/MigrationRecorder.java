package org.example.infrastructure.migration;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class MigrationRecorder {

    private final DynamoDbClient dynamoDbClient;

    public MigrationRecorder(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    /**
     * Check that the view migration process has run.
     */
    public boolean hasMigrationRun(String migrationId) {
        GetItemRequest getRequest = GetItemRequest.builder()
                .tableName("Migrations")
                .key(Map.of("MigrationId", AttributeValue.builder().s(migrationId).build()))
                .build();

        Map<String, AttributeValue> result = dynamoDbClient.getItem(getRequest).item();
        return result != null && !result.isEmpty();
    }

    /**
     * Record migrations in the Migrations table.
     */
    public void recordMigration(String migrationId, String status) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("MigrationId", AttributeValue.builder().s(migrationId).build());
        item.put("Timestamp", AttributeValue.builder().s(Instant.now().toString()).build());
        item.put("Status", AttributeValue.builder().s(status).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("Migrations")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        System.out.println("✅ Record migrations: " + migrationId + " - Status: " + status);
    }
}
