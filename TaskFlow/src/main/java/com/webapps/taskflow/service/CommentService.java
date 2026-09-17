package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.comment.CommentCreateRequest;
import com.webapps.taskflow.dtos.comment.CommentResponse;
import com.webapps.taskflow.entity.Comment;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.CommentMapper;
import com.webapps.taskflow.repository.CommentRepository;
import com.webapps.taskflow.repository.TaskRepository;
import com.webapps.taskflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> listByTask(Long taskId) {
        findTaskOrThrow(taskId);
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId).stream()
                .map(CommentMapper::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse create(Long taskId, CommentCreateRequest request) {
        Task task = findTaskOrThrow(taskId);
        User author = findUserOrThrow(request.authorId());
        Comment comment = CommentMapper.toEntity(request, author, task);
        return CommentMapper.toResponse(commentRepository.save(comment));
    }

    private Task findTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + taskId));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }
}
