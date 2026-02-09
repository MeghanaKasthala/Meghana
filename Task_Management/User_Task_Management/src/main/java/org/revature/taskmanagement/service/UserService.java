package org.revature.taskmanagement.service;

import java.util.List;
import java.util.Optional;

import org.revature.taskmanagement.dto.UserDTO;
import org.revature.taskmanagement.enums.UserStatus;
import org.revature.taskmanagement.model.Role;
import org.revature.taskmanagement.model.User;
import org.revature.taskmanagement.repository.RoleRepository;
import org.revature.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return null;
        }

        Optional<Role> roleOpt = roleRepository.findById(dto.getRoleId());
        if (roleOpt.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(roleOpt.get());
        user.setStatus(UserStatus.ACTIVE);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserStatus(Long id, UserStatus status) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setStatus(status);
        return userRepository.save(user);
    }
}