package com.webapps.taskflow.dtos.task;

import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Status;

import java.util.List;

public record TaskResponse(
        Long id,
        String key,
        String name,
        UserResponse assignee,
        Status status,
        Priority priority,
        List<String> labels,
        Long projectId
) {
}
