package org.example.taskmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.UserRequestDTO;
import org.example.taskmanagement.dto.UserStatusUpdateDTO;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.UserRepository;
import org.example.taskmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@Valid @RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}/status")
    public User updateStatus(@PathVariable Long id,
                             @RequestBody UserStatusUpdateDTO dto) {
        return userService.updateUserStatus(id, dto.getStatus());
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
