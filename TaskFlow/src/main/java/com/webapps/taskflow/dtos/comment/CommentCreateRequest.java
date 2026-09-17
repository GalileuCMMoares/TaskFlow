package com.webapps.taskflow.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
        @NotBlank String content,
        @NotNull Long authorId
) {
}
