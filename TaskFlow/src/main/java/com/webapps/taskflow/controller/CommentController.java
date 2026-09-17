package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.comment.CommentCreateRequest;
import com.webapps.taskflow.dtos.comment.CommentResponse;
import com.webapps.taskflow.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> listAll(@PathVariable Long taskId) {
        return commentService.listByTask(taskId);
    }

    @PostMapping
    public CommentResponse create(@PathVariable Long taskId, @Valid @RequestBody CommentCreateRequest request) {
        return commentService.create(taskId, request);
    }
}
