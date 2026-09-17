package com.webapps.taskflow.dtos.task;

import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TaskUpdateRequest(
        @NotBlank String name,
        @NotNull Long assigneeId,
        @NotNull Status status,
        @NotNull Priority priority,
        @NotNull Long projectId,
        List<String> labels
) {
}
