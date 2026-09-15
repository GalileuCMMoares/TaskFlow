package com.webapps.taskflow.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@SQLRestriction("deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime deletedAt;

    public Task(String name, User assignee, Status status) {
        this.name = name;
        this.assignee = assignee;
        this.status = status;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
