package org.example.spring_demo_app.Repository;

import org.example.spring_demo_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
