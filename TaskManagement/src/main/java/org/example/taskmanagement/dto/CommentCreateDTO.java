package org.example.taskmanagement.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {

    private Long taskId;
    private String text;
}
