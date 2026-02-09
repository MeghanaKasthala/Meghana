package org.revature.taskmanagement.service;

import java.util.List;
import java.util.Optional;

import org.revature.taskmanagement.model.Role;
import org.revature.taskmanagement.repository.RoleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @CacheEvict(value = "roles", allEntries = true)
    public Role createRole(String name) {
        if (roleRepository.existsByName(name)) {
            return null;
        }
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Cacheable("roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        return roleOpt.orElse(null);
    }

    @CacheEvict(value = "roles", allEntries = true)
    public boolean deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }
        roleRepository.deleteById(id);
        return true;
    }
}
