package org.example.taskmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_history")
@Data
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private TaskStatus newStatus;

    private String reason;   // 👈 ADD THIS LINE

    private LocalDateTime changedAt = LocalDateTime.now();

    @ManyToOne
    private Task task;
}
