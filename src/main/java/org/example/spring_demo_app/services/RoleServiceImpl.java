package org.example.spring_demo_app.services;

import org.example.spring_demo_app.models.Role;
import org.example.spring_demo_app.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> findAllRoles() {
        return repository.findAll();
    }
}
