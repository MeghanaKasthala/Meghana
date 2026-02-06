package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.TaskCreateDTO;
import org.example.taskmanagement.dto.TaskStatusUpdateDTO;
import org.example.taskmanagement.model.*;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.TaskHistoryRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final NotificationService notificationService;

    // ================= CREATE TASK =================

    public Task createTask(TaskCreateDTO dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (project.getStatus() == ProjectStatus.ARCHIVED) {
            throw new RuntimeException("Cannot create task under archived project");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Task must be assigned to ACTIVE user");
        }

        if (dto.getDueDate() != null && dto.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Due date must be in future");
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setProject(project);
        task.setAssignedUser(user);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(dto.getDueDate());

        Task savedTask = taskRepository.save(task);

        // Notification: task assigned
        notificationService.notify(user,
                "Task assigned: " + task.getTitle());

        return savedTask;
    }

    // ================= UPDATE TASK STATUS =================

    public Task updateTaskStatus(Long taskId, TaskStatusUpdateDTO dto) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // COMPLETED is immutable
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Completed tasks cannot be modified");
        }

        TaskStatus newStatus = TaskStatus.valueOf(dto.getStatus());
        TaskStatus oldStatus = task.getStatus();

        // BLOCKED requires reason
        if (newStatus == TaskStatus.BLOCKED &&
                (dto.getReason() == null || dto.getReason().isBlank())) {
            throw new RuntimeException("Blocked tasks require reason");
        }

        // Update task
        task.setStatus(newStatus);
        taskRepository.save(task);

        // Save history
        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setReason(dto.getReason());

        taskHistoryRepository.save(history);

        // Notifications
        if (newStatus == TaskStatus.BLOCKED) {
            notificationService.notify(task.getAssignedUser(),
                    "Task blocked: " + dto.getReason());
        } else {
            notificationService.notify(task.getAssignedUser(),
                    "Task status changed to " + newStatus);
        }

        return task;
    }
}
