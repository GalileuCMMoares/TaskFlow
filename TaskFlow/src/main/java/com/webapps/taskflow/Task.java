package com.webapps.taskflow;

import jakarta.persistence.*;

@Entity
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

    protected Task() {}

    public Task(String name, User assignee, Status status) {
        this.name = name;
        this.assignee = assignee;
        this.status = status;
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
}
