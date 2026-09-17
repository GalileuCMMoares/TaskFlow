package com.webapps.taskflow.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLRestriction("deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @Setter
    private String name;
    @Setter
    private String description;
    private LocalDateTime deletedAt;
    private int nextTaskNumber;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public Project(String key, String name, String description) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.nextTaskNumber = 1;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }

    public String nextTaskKey() {
        String taskKey = key + "-" + nextTaskNumber;
        nextTaskNumber++;
        return taskKey;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        tasks.forEach(Task::softDelete);
    }
}
