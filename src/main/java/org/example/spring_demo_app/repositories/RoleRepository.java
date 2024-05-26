package org.example.spring_demo_app.repositories;

import org.example.spring_demo_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String name);
}
