package com.webapps.taskflow.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLRestriction("deleted_at IS NULL")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    protected Project() {}

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        tasks.forEach(Task::softDelete);
    }
}
