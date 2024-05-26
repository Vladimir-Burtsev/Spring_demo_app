package org.example.spring_demo_app.services;

import org.example.spring_demo_app.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
}
