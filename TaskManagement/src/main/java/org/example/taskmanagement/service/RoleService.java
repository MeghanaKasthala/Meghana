package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Role;
import org.example.taskmanagement.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createRole(Role role) {

        if (roleRepository.existsByName(role.getName())) {
            throw new RuntimeException("Role already exists");
        }

        return roleRepository.save(role);
    }
}
