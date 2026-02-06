package org.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ProjectCreateDTO {

    private String name;

    // optional manager
    private Long managerId;
}
