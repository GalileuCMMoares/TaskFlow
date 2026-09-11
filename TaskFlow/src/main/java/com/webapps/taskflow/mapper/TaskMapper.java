package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;

public final class TaskMapper {

    private TaskMapper() {}

    public static Task toEntity(TaskCreateRequest request, User assignee, Project project) {
        Task task = new Task(request.name(), assignee, request.status());
        project.addTask(task);
        return task;
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getName(),
                UserMapper.toResponse(task.getAssignee()),
                task.getStatus(),
                task.getProject().getId()
        );
    }

    public static void applyUpdate(TaskUpdateRequest request, Task task, User assignee, Project project) {
        task.setName(request.name());
        task.setAssignee(assignee);
        task.setStatus(request.status());
        task.setProject(project);
    }
}
