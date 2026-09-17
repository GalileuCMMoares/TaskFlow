package com.webapps.taskflow.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void softDeleteSetsDeletedAt() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.MEDIUM);

        task.softDelete();

        assertThat(task.getDeletedAt()).isNotNull();
    }

    @Test
    void newTaskHasNoProjectUntilAddedToOne() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.MEDIUM);

        assertThat(task.getProject()).isNull();
    }

    @Test
    void newTaskHasNoKeyUntilAssigned() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.MEDIUM);

        assertThat(task.getKey()).isNull();

        task.assignKey("WEB-1");

        assertThat(task.getKey()).isEqualTo("WEB-1");
    }
}
