package com.webapps.taskflow.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void addTaskSynchronizesBothSides() {
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.MEDIUM);

        project.addTask(task);

        assertThat(project.getTasks()).contains(task);
        assertThat(task.getProject()).isEqualTo(project);
    }

    @Test
    void softDeleteCascadesToTasks() {
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.MEDIUM);
        project.addTask(task);

        project.softDelete();

        assertThat(project.getDeletedAt()).isNotNull();
        assertThat(task.getDeletedAt()).isNotNull();
    }

    @Test
    void nextTaskKeyIncrementsSequentially() {
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");

        assertThat(project.nextTaskKey()).isEqualTo("WEB-1");
        assertThat(project.nextTaskKey()).isEqualTo("WEB-2");
    }
}
