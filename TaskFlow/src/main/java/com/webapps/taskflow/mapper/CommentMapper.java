package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.comment.CommentCreateRequest;
import com.webapps.taskflow.dtos.comment.CommentResponse;
import com.webapps.taskflow.entity.Comment;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;

public final class CommentMapper {

    private CommentMapper() {}

    public static Comment toEntity(CommentCreateRequest request, User author, Task task) {
        return new Comment(request.content(), author, task);
    }

    public static CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                UserMapper.toResponse(comment.getAuthor()),
                comment.getCreatedAt()
        );
    }
}
