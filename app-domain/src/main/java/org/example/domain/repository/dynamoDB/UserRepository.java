package org.example.domain.repository.dynamoDB;

import org.example.domain.model.dynamoDB.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
    void delete(String id);
}
