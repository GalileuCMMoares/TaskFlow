package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.TaskMapper;
import com.webapps.taskflow.repository.ProjectRepository;
import com.webapps.taskflow.repository.TaskRepository;
import com.webapps.taskflow.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(TaskMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> listByProject(Long projectId, Pageable pageable) {
        findProjectOrThrow(projectId);
        return taskRepository.findByProjectId(projectId, pageable)
                .map(TaskMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Long id) {
        return TaskMapper.toResponse(findByIdOrThrow(id));
    }

    @Transactional
    public TaskResponse create(TaskCreateRequest request) {
        User assignee = findUserOrThrow(request.assigneeId());
        Project project = findProjectOrThrow(request.projectId());
        Task task = TaskMapper.toEntity(request, assignee, project);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse update(Long id, TaskUpdateRequest request) {
        Task task = findByIdOrThrow(id);
        User assignee = findUserOrThrow(request.assigneeId());
        Project project = findProjectOrThrow(request.projectId());
        TaskMapper.applyUpdate(request, task, assignee, project);
        return TaskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(Long id) {
        Task task = findByIdOrThrow(id);
        task.softDelete();
        taskRepository.save(task);
    }

    private Task findByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + id));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
    }
}
