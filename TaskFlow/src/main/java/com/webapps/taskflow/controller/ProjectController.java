package com.webapps.taskflow.controller;

import com.webapps.taskflow.entity.Project;
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
    public List<Project> listAll(){
        return projectRepository.findAll();
    }

    @PostMapping
    public Project create(@RequestBody Project project){
        return projectRepository.save(project);
    }
}
