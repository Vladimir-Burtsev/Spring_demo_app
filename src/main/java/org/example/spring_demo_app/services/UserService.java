package org.example.spring_demo_app.services;

import org.example.spring_demo_app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(long id);
    Optional<User> findByUsername(String username);

    List<User> findAllUsers();

    User saveUser(User user);

    void deleteUserById(long id);

    User updateUser(User user);

}
