package com.webapps.taskflow.dtos.comment;

import com.webapps.taskflow.dtos.user.UserResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        UserResponse author,
        LocalDateTime createdAt
) {
}
