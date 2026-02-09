package org.revature.taskmanagement.controller;

import java.util.List;

import org.revature.taskmanagement.dto.UserDTO;
import org.revature.taskmanagement.enums.UserStatus;
import org.revature.taskmanagement.model.User;
import org.revature.taskmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto) {
        User user = userService.createUser(dto);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid role or email exists");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestParam UserStatus status) {
        User user = userService.updateUserStatus(id, status);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}