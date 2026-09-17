package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.comment.CommentCreateRequest;
import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Status;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.repository.CommentRepository;
import com.webapps.taskflow.repository.TaskRepository;
import com.webapps.taskflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, taskRepository, userRepository);
    }

    @Test
    void listByTaskThrowsNotFoundWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.listByTask(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void createThrowsNotFoundWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        CommentCreateRequest request = new CommentCreateRequest("Ficou ótimo!", 1L);

        assertThatThrownBy(() -> commentService.create(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void createThrowsNotFoundWhenAuthorDoesNotExist() {
        var task = new Task(
                "Criar wireframe",
                new User("ana@taskflow.dev", "Ana Souza"),
                Status.PENDING,
                Priority.MEDIUM
        );
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        CommentCreateRequest request = new CommentCreateRequest("Ficou ótimo!", 2L);

        assertThatThrownBy(() -> commentService.create(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
    }
}
