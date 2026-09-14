package com.webapps.taskflow.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@SQLRestriction("deleted_at IS NULL")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime deletedAt;

    protected Task() {}

    public Task(String name, User assignee, Status status) {
        this.name = name;
        this.assignee = assignee;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAssignee() {
        return assignee;
    }

    public Status getStatus() {
        return status;
    }

    public Project getProject() {
        return project;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
