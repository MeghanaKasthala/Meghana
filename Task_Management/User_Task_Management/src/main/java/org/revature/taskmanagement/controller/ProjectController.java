package org.revature.taskmanagement.controller;

import java.util.List;

import org.revature.taskmanagement.dto.ProjectDTO;
import org.revature.taskmanagement.enums.ProjectStatus;
import org.revature.taskmanagement.model.Project;
import org.revature.taskmanagement.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO dto) {
        Project project = projectService.createProject(dto);
        if (project == null) {
            return ResponseEntity.badRequest().body("Manager not found");
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return ResponseEntity.badRequest().body("Project not found");
        }
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestParam ProjectStatus status) {
        Project project = projectService.updateProjectStatus(id, status);
        if (project == null) {
            return ResponseEntity.badRequest().body("Invalid update");
        }
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        if (!deleted) {
            return ResponseEntity.badRequest().body("Project not found");
        }
        return ResponseEntity.ok("Project deleted");
    }
}