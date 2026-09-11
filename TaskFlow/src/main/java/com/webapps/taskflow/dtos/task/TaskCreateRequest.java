package com.webapps.taskflow.dtos.task;

import com.webapps.taskflow.entity.Status;

public record TaskCreateRequest(String name, Long assigneeId, Status status, Long projectId) {
}
