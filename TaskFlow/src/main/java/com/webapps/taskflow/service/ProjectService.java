package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.project.ProjectCreateRequest;
import com.webapps.taskflow.dtos.project.ProjectResponse;
import com.webapps.taskflow.dtos.project.ProjectUpdateRequest;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.mapper.ProjectMapper;
import com.webapps.taskflow.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponse> listAll(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(ProjectMapper::toResponse);
    }

    @Transactional
    public ProjectResponse create(ProjectCreateRequest request) {
        Project project = ProjectMapper.toEntity(request);
        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public ProjectResponse update(Long id, ProjectUpdateRequest request) {
        Project project = findByIdOrThrow(id);
        ProjectMapper.applyUpdate(request, project);
        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public void delete(Long id) {
        Project project = findByIdOrThrow(id);
        project.softDelete();
        projectRepository.save(project);
    }

    private Project findByIdOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
    }
}
