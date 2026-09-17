package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Status;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.repository.ProjectRepository;
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
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, userRepository, projectRepository);
    }

    @Test
    void createThrowsNotFoundWhenAssigneeDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        TaskCreateRequest request = new TaskCreateRequest("Criar wireframe", 1L, Status.PENDING, Priority.MEDIUM, 1L, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void createThrowsNotFoundWhenProjectDoesNotExist() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        when(userRepository.findById(1L)).thenReturn(Optional.of(assignee));
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        TaskCreateRequest request = new TaskCreateRequest("Criar wireframe", 1L, Status.PENDING, Priority.MEDIUM, 1L, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Project not found");
    }

    @Test
    void updateThrowsNotFoundWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        TaskUpdateRequest request = new TaskUpdateRequest("New name", 1L, Status.DONE, Priority.MEDIUM, 1L, null);

        assertThatThrownBy(() -> taskService.update(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void deleteThrowsNotFoundWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Task not found");
    }
}
