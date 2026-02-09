package org.revature.taskmanagement.projection;

import org.revature.taskmanagement.enums.TaskStatus;

public interface TaskProjection {
    Long getId();
    String getTitle();
    TaskStatus getStatus();
}
