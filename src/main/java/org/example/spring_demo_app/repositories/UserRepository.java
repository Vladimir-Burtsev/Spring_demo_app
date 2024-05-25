package org.example.spring_demo_app.repositories;

import org.example.spring_demo_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.roles where u.username=:username")
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
