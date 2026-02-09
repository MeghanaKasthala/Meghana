package org.revature.taskmanagement.service;

import java.util.List;
import java.util.Optional;

import org.revature.taskmanagement.dto.TaskDTO;
import org.revature.taskmanagement.enums.ProjectStatus;
import org.revature.taskmanagement.enums.TaskStatus;
import org.revature.taskmanagement.enums.UserStatus;
import org.revature.taskmanagement.model.Project;
import org.revature.taskmanagement.model.Task;
import org.revature.taskmanagement.model.User;
import org.revature.taskmanagement.projection.TaskProjection;
import org.revature.taskmanagement.repository.ProjectRepository;
import org.revature.taskmanagement.repository.TaskRepository;
import org.revature.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Task createTask(TaskDTO dto) {

        Optional<Project> projectOpt = projectRepository.findById(dto.getProjectId());
        if (projectOpt.isEmpty()) {
            return null;
        }

        Project project = projectOpt.get();
        if (project.getStatus() == ProjectStatus.ARCHIVED) {
            return null;
        }

        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        if (user.getStatus() != UserStatus.ACTIVE) {
            return null;
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setProject(project);
        task.setAssignedUser(user);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(dto.getDueDate());

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task updateTaskStatus(Long taskId, TaskStatus newStatus, String reason) {

        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            return null;
        }

        if (newStatus == TaskStatus.BLOCKED &&
                (reason == null || reason.trim().isEmpty())) {
            return null;
        }

        task.setStatus(newStatus);

        if (newStatus == TaskStatus.BLOCKED) {
            task.setBlockedReason(reason);
        } else {
            task.setBlockedReason(null);
        }

        return taskRepository.save(task);
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    public List<TaskProjection> getTaskSummaries() {
        return taskRepository.findAllProjectedBy();
    }
}
