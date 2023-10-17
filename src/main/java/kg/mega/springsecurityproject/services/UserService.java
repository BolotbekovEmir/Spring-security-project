package kg.mega.springsecurityproject.services;

import kg.mega.springsecurityproject.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    void createNewUser(User user);
}
