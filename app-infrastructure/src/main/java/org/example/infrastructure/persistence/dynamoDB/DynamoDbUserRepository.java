package org.example.infrastructure.persistence.dynamoDB;

import org.example.domain.model.dynamoDB.User;
import org.example.domain.repository.dynamoDB.UserRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DynamoDbUserRepository implements UserRepository {
    private final DynamoDbTable<User> userTable;

    public DynamoDbUserRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.userTable = enhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    // Lưu hoặc cập nhật User
    @Override
    public User save(User user) {
        userTable.putItem(user);
        return user;
    }

    // Tìm User theo ID
    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    // Lấy danh sách tất cả User
    @Override
    public List<User> findAll() {
        return userTable.scan().items().stream().collect(Collectors.toList());
    }

    // Xóa User theo ID
    @Override
    public void delete(String id) {
        userTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
