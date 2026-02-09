package org.revature.taskmanagement.repository;

import org.revature.taskmanagement.model.Task;
import org.revature.taskmanagement.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedUserId(Long userId);

    List<Task> findByProjectId(Long projectId);

    List<TaskProjection> findAllProjectedBy();
}
