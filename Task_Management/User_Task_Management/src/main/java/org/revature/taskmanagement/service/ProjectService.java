package org.revature.taskmanagement.service;

import java.util.List;
import java.util.Optional;

import org.revature.taskmanagement.dto.ProjectDTO;
import org.revature.taskmanagement.enums.ProjectStatus;
import org.revature.taskmanagement.model.Project;
import org.revature.taskmanagement.model.User;
import org.revature.taskmanagement.repository.ProjectRepository;
import org.revature.taskmanagement.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "projects", allEntries = true)
    public Project createProject(ProjectDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setStatus(ProjectStatus.ACTIVE);

        if (dto.getManagerId() != null) {
            Optional<User> managerOpt = userRepository.findById(dto.getManagerId());
            if (managerOpt.isEmpty()) {
                return null;
            }
            project.setManager(managerOpt.get());
        }

        return projectRepository.save(project);
    }

    @Cacheable("projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "projects", allEntries = true)
    public Project updateProjectStatus(Long id, ProjectStatus status) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            return null;
        }

        Project project = projectOpt.get();
        if (project.getStatus() == ProjectStatus.COMPLETED) {
            return null;
        }

        project.setStatus(status);
        return projectRepository.save(project);
    }

    @CacheEvict(value = "projects", allEntries = true)
    public boolean deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            return false;
        }
        projectRepository.deleteById(id);
        return true;
    }
}
