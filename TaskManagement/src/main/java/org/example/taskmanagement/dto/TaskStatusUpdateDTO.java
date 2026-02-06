package org.example.taskmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusUpdateDTO {
    private String status;
    private String reason;
}
