package com.webapps.taskflow.controller;

import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> listAll(){
        return taskRepository.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task){
        return taskRepository.save(task);
    }
}
