package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.ProjectCreateDTO;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.ProjectStatus;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @PostMapping
    public Project createProject(@RequestBody ProjectCreateDTO dto) {
        return projectService.createProject(dto);
    }

    @PatchMapping("/{id}/status")
    public Project updateStatus(@PathVariable Long id,
                                @RequestParam ProjectStatus status) {

        return projectService.updateProjectStatus(id, status);
    }

    @GetMapping
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
