package org.example.spring_demo_app.service;

import org.example.spring_demo_app.model.User;

import java.util.List;

public interface UserService {
    User findUserById(long id);

    List<User> findAllUsers();

    User saveUser(User user);

    void deleteUserById(long id);

    User updateUser(User user);
}
