package org.example.infrastructure.migration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DynamoDbMigrationRunner implements CommandLineRunner {
    private final DynamoDbMigrationService migrationService;

    public DynamoDbMigrationRunner(DynamoDbMigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Ensuring DynamoDB Migration Runs First...");
        migrationService.migrate();
    }
}