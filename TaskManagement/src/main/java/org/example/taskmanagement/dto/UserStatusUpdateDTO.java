package org.example.taskmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmanagement.model.UserStatus;

@Data
public class UserStatusUpdateDTO {

    @NotNull
    private UserStatus status;
}
