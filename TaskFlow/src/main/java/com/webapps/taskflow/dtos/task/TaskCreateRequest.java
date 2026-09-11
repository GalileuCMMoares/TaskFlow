package com.webapps.taskflow.dtos.task;

import com.webapps.taskflow.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateRequest(
        @NotBlank String name,
        @NotNull Long assigneeId,
        @NotNull Status status,
        @NotNull Long projectId
) { }
