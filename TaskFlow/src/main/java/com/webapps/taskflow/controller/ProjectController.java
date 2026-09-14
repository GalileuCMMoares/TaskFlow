package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.project.ProjectCreateRequest;
import com.webapps.taskflow.dtos.project.ProjectResponse;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.mapper.ProjectMapper;
import com.webapps.taskflow.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public Page<ProjectResponse> listAll(Pageable pageable){
        return projectRepository.findAll(pageable)
                .map(ProjectMapper::toResponse);
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectCreateRequest request){
        Project project = ProjectMapper.toEntity(request);
        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        project.softDelete();
        projectRepository.save(project);
    }
}
