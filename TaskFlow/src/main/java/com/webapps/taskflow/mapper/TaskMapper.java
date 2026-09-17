package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;

import java.util.ArrayList;

public final class TaskMapper {

    private TaskMapper() {}

    public static Task toEntity(TaskCreateRequest request, User assignee, Project project) {
        Task task = new Task(request.name(), assignee, request.status(), request.priority());
        task.setLabels(request.labels() != null ? new ArrayList<>(request.labels()) : new ArrayList<>());
        project.addTask(task);
        task.assignKey(project.nextTaskKey());
        return task;
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getKey(),
                task.getName(),
                UserMapper.toResponse(task.getAssignee()),
                task.getStatus(),
                task.getPriority(),
                task.getLabels(),
                task.getProject().getId()
        );
    }

    public static void applyUpdate(TaskUpdateRequest request, Task task, User assignee, Project project) {
        task.setName(request.name());
        task.setAssignee(assignee);
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setLabels(request.labels() != null ? new ArrayList<>(request.labels()) : new ArrayList<>());
        task.setProject(project);
    }
}
