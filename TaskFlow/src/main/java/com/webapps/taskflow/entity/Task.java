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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @Setter
    private String name;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Setter
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Setter
    @ElementCollection
    @CollectionTable(name = "task_labels", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "label")
    private List<String> labels = new ArrayList<>();

    private LocalDateTime deletedAt;

    public Task(String name, User assignee, Status status, Priority priority) {
        this.name = name;
        this.assignee = assignee;
        this.status = status;
        this.priority = priority;
    }

    public void assignKey(String key) {
        this.key = key;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
