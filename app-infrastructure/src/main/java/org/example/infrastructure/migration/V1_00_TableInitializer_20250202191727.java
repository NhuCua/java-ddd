package org.example.infrastructure.migration;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;


@Component
public class V1_00_TableInitializer_20250202191727 {

    private final DynamoDbClient dynamoDbClient;

    public V1_00_TableInitializer_20250202191727(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void createTables() {
        createTable("Migrations", "MigrationId");
        createTable("Users", "userId");
    }

    private void createTable(String tableName, String primaryKey) {
        boolean tableExists = dynamoDbClient.listTables().tableNames().contains(tableName);
        if (tableExists) {
            System.out.println("✅ Table " + tableName + " existed.");
            return;
        }

        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(tableName)
                .keySchema(KeySchemaElement.builder()
                        .attributeName(primaryKey)
                        .keyType(KeyType.HASH) 
                        .build())
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(primaryKey)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L)
                        .writeCapacityUnits(5L)
                        .build())
                .build();

        dynamoDbClient.createTable(request);
        System.out.println("✅ Create a table " + tableName + " success!");
    }
}
