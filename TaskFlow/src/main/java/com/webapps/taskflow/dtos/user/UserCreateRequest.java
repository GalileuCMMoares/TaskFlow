package com.webapps.taskflow.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @NotBlank String name,
        @NotBlank @Email String email
) { }
