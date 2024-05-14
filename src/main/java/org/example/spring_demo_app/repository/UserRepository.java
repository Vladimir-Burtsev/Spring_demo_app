package org.example.spring_demo_app.repository;

import org.example.spring_demo_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
