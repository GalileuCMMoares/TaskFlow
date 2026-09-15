package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Status;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    void toEntityAddsTaskToTheGivenProject() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Project project = new Project("Website Redesign", "Redesign da landing page");
        TaskCreateRequest request = new TaskCreateRequest("Criar wireframe", 1L, Status.PENDING, 1L);

        Task task = TaskMapper.toEntity(request, assignee, project);

        assertThat(task.getName()).isEqualTo("Criar wireframe");
        assertThat(task.getAssignee()).isEqualTo(assignee);
        assertThat(task.getStatus()).isEqualTo(Status.PENDING);
        assertThat(task.getProject()).isEqualTo(project);
        assertThat(project.getTasks()).contains(task);
    }

    @Test
    void toResponseMapsAssigneeAndProjectId() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Project project = new Project("Website Redesign", "Redesign da landing page");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING);
        project.addTask(task);

        TaskResponse response = TaskMapper.toResponse(task);

        assertThat(response.id()).isEqualTo(task.getId());
        assertThat(response.name()).isEqualTo("Criar wireframe");
        assertThat(response.assignee().email()).isEqualTo("ana@taskflow.dev");
        assertThat(response.status()).isEqualTo(Status.PENDING);
        assertThat(response.projectId()).isEqualTo(project.getId());
    }

    @Test
    void applyUpdateOverwritesAllFields() {
        User oldAssignee = new User("old@taskflow.dev", "Old Assignee");
        Project oldProject = new Project("Old Project", "Old description");
        Task task = new Task("Old name", oldAssignee, Status.PENDING);
        oldProject.addTask(task);

        User newAssignee = new User("new@taskflow.dev", "New Assignee");
        Project newProject = new Project("New Project", "New description");
        TaskUpdateRequest request = new TaskUpdateRequest("New name", 2L, Status.DONE, 2L);

        TaskMapper.applyUpdate(request, task, newAssignee, newProject);

        assertThat(task.getName()).isEqualTo("New name");
        assertThat(task.getAssignee()).isEqualTo(newAssignee);
        assertThat(task.getStatus()).isEqualTo(Status.DONE);
        assertThat(task.getProject()).isEqualTo(newProject);
    }
}
