package com.webapps.taskflow.dtos.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectUpdateRequest(
        @NotBlank String name,
        @NotBlank String description
) {
}
