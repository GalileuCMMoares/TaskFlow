package com.webapps.taskflow.dtos.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectCreateRequest(
        @NotBlank String key,
        @NotBlank String name,
        @NotBlank String description
) { }
