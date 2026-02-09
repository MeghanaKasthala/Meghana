package org.revature.taskmanagement.controller;

import java.util.List;

import org.revature.taskmanagement.dto.TaskDTO;
import org.revature.taskmanagement.enums.TaskStatus;
import org.revature.taskmanagement.model.Task;
import org.revature.taskmanagement.projection.TaskProjection;
import org.revature.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO dto) {
        Task task = taskService.createTask(dto);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid project or user");
        }

        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body("Task not found");
        }

        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<TaskProjection>> getTaskSummary() {
        return ResponseEntity.ok(taskService.getTaskSummaries());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            @RequestParam(required = false) String reason) {

        Task task = taskService.updateTaskStatus(id, status, reason);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid task status update");
        }

        return ResponseEntity.ok(task);
    }
}
