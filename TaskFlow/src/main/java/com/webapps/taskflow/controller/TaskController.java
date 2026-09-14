package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.TaskMapper;
import com.webapps.taskflow.repository.ProjectRepository;
import com.webapps.taskflow.repository.TaskRepository;
import com.webapps.taskflow.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public Page<TaskResponse> listAll(Pageable pageable){
        return taskRepository.findAll(pageable)
                .map(TaskMapper::toResponse);
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest request){
        User assignee = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + request.assigneeId()));
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + request.projectId()));

        Task task = TaskMapper.toEntity(request, assignee, project);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + id));
        task.softDelete();
        taskRepository.save(task);
    }
}
