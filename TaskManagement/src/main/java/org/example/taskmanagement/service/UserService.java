package org.example.taskmanagement.service;
import org.example.taskmanagement.model.UserStatus;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.UserRequestDTO;
import org.example.taskmanagement.model.Role;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.RoleRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User createUser(UserRequestDTO dto) {

        // 1. Email uniqueness
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 2. Validate role
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3. Create user
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);

        // 4. Save
        return userRepository.save(user);
    }
    public User updateUserStatus(Long userId, UserStatus newStatus) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(newStatus);

        return userRepository.save(user);
    }

}
