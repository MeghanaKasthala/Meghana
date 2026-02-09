package org.revature.taskmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.revature.taskmanagement.enums.TaskStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus newStatus;

    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;
}