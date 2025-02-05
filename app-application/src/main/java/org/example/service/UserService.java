package org.example.service;

import org.example.domain.model.dynamoDB.User;
import org.example.domain.repository.dynamoDB.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(String name, String email) {
        User user = new User(null, name, email);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }
}
