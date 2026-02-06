package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.TaskStatus;
import org.example.taskmanagement.model.UserStatus;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Map<String, Object> getDashboard() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalTasks", taskRepository.count());
        data.put("completedTasks", taskRepository.countByStatus(TaskStatus.COMPLETED));
        data.put("blockedTasks", taskRepository.countByStatus(TaskStatus.BLOCKED));

        data.put("totalUsers", userRepository.count());
        data.put("activeUsers", userRepository.countByStatus(UserStatus.ACTIVE));

        data.put("totalProjects", projectRepository.count());

        return data;
    }
}
