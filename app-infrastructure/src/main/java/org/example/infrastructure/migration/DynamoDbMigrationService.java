package org.example.infrastructure.migration;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;


@Service
public class DynamoDbMigrationService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbMigrationService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void migrate() {
        System.out.println("🚀 Running DynamoDB Migration...");
        createTableIfNotExists("Users", "id");
    }

    private void createTableIfNotExists(String tableName, String partitionKey) {
        ListTablesResponse tables = dynamoDbClient.listTables();
        if (tables.tableNames().contains(tableName)) {
            System.out.println("✅ Table " + tableName + " already exists.");
            return;
        }

        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(tableName)
                .keySchema(KeySchemaElement.builder()
                        .attributeName(partitionKey)
                        .keyType(KeyType.HASH)
                        .build())
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(partitionKey)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L)
                        .writeCapacityUnits(5L)
                        .build())
                .build();

        dynamoDbClient.createTable(request);
        System.out.println("🚀 Table " + tableName + " created successfully!");
    }

}
