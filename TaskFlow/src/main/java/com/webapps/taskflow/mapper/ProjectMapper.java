package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.project.ProjectCreateRequest;
import com.webapps.taskflow.dtos.project.ProjectResponse;
import com.webapps.taskflow.dtos.project.ProjectUpdateRequest;
import com.webapps.taskflow.entity.Project;

public final class ProjectMapper {

    private ProjectMapper() {}

    public static Project toEntity(ProjectCreateRequest request) {
        return new Project(request.key().toUpperCase(), request.name(), request.description());
    }

    public static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getKey(),
                project.getName(),
                project.getDescription()
        );
    }

    public static void applyUpdate(ProjectUpdateRequest request, Project project) {
        project.setName(request.name());
        project.setDescription(request.description());
    }
}
