package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.ProjectCreateDTO;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.ProjectStatus;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // ================= CREATE PROJECT =================

    public Project createProject(ProjectCreateDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Project name is required");
        }

        Project project = new Project();
        project.setName(dto.getName());
        project.setStatus(ProjectStatus.ACTIVE);

        if (dto.getManagerId() != null) {
            User manager = userRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            project.setManager(manager);
        }

        return projectRepository.save(project);
    }

    // ================= UPDATE PROJECT STATUS =================

    public Project updateProjectStatus(Long projectId, ProjectStatus newStatus) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (project.getStatus() == ProjectStatus.ARCHIVED) {
            throw new RuntimeException("Archived project cannot be modified");
        }

        project.setStatus(newStatus);

        Project saved = projectRepository.save(project);

        // Notification when project is completed
        if (newStatus == ProjectStatus.COMPLETED && saved.getManager() != null) {
            notificationService.notify(saved.getManager(),
                    "Project completed: " + saved.getName());
        }

        return saved;
    }
}
