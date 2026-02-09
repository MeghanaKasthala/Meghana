package org.revature.taskmanagement.controller;

import java.util.List;

import org.revature.taskmanagement.model.Role;
import org.revature.taskmanagement.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestParam String name) {
        Role role = roleService.createRole(name);
        if (role == null) {
            return ResponseEntity.badRequest().body("Role already exists");
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        if (role == null) {
            return ResponseEntity.badRequest().body("Role not found");
        }
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        boolean deleted = roleService.deleteRole(id);
        if (!deleted) {
            return ResponseEntity.badRequest().body("Role not found");
        }
        return ResponseEntity.ok("Role deleted");
    }
}