package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.project.ProjectCreateRequest;
import com.webapps.taskflow.dtos.project.ProjectResponse;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.mapper.ProjectMapper;
import com.webapps.taskflow.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<ProjectResponse> listAll(){
        return projectRepository.findAll().stream()
                .map(ProjectMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ProjectResponse create(@RequestBody ProjectCreateRequest request){
        Project project = ProjectMapper.toEntity(request);
        return ProjectMapper.toResponse(projectRepository.save(project));
    }
}
