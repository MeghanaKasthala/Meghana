package org.example.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDTO {

    private String title;

    private Long projectId;

    private Long userId;

    private LocalDate dueDate;
}
