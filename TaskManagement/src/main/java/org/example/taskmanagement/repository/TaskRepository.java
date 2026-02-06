package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(TaskStatus status);
}
