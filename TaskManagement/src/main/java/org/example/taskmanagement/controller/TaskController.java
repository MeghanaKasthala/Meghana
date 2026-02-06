package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.TaskCreateDTO;
import org.example.taskmanagement.dto.TaskStatusUpdateDTO;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @PostMapping
    public Task createTask(@RequestBody TaskCreateDTO dto) {
        return taskService.createTask(dto);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestBody TaskStatusUpdateDTO dto) {
        return taskService.updateTaskStatus(id, dto);
    }

    @GetMapping
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
