package com.webapps.taskflow.dtos.task;

import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.entity.Status;

public record TaskResponse(Long id, String name, UserResponse assignee, Status status, Long projectId) {
}
