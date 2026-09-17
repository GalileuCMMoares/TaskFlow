package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.task.TaskCreateRequest;
import com.webapps.taskflow.dtos.task.TaskResponse;
import com.webapps.taskflow.dtos.task.TaskUpdateRequest;
import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Project;
import com.webapps.taskflow.entity.Status;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    void toEntityAddsTaskToTheGivenProjectAndAssignsKey() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");
        TaskCreateRequest request = new TaskCreateRequest(
                "Criar wireframe", 1L, Status.PENDING, Priority.HIGH, 1L, List.of("frontend")
        );

        Task task = TaskMapper.toEntity(request, assignee, project);

        assertThat(task.getName()).isEqualTo("Criar wireframe");
        assertThat(task.getAssignee()).isEqualTo(assignee);
        assertThat(task.getStatus()).isEqualTo(Status.PENDING);
        assertThat(task.getPriority()).isEqualTo(Priority.HIGH);
        assertThat(task.getLabels()).containsExactly("frontend");
        assertThat(task.getKey()).isEqualTo("WEB-1");
        assertThat(task.getProject()).isEqualTo(project);
        assertThat(project.getTasks()).contains(task);
    }

    @Test
    void toResponseMapsAssigneeAndProjectId() {
        User assignee = new User("ana@taskflow.dev", "Ana Souza");
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");
        Task task = new Task("Criar wireframe", assignee, Status.PENDING, Priority.LOW);
        project.addTask(task);
        task.assignKey(project.nextTaskKey());

        TaskResponse response = TaskMapper.toResponse(task);

        assertThat(response.id()).isEqualTo(task.getId());
        assertThat(response.key()).isEqualTo("WEB-1");
        assertThat(response.name()).isEqualTo("Criar wireframe");
        assertThat(response.assignee().email()).isEqualTo("ana@taskflow.dev");
        assertThat(response.status()).isEqualTo(Status.PENDING);
        assertThat(response.priority()).isEqualTo(Priority.LOW);
        assertThat(response.projectId()).isEqualTo(project.getId());
    }

    @Test
    void applyUpdateOverwritesAllFields() {
        User oldAssignee = new User("old@taskflow.dev", "Old Assignee");
        Project oldProject = new Project("OLD", "Old Project", "Old description");
        Task task = new Task("Old name", oldAssignee, Status.PENDING, Priority.LOW);
        oldProject.addTask(task);

        User newAssignee = new User("new@taskflow.dev", "New Assignee");
        Project newProject = new Project("NEW", "New Project", "New description");
        TaskUpdateRequest request = new TaskUpdateRequest(
                "New name", 2L, Status.DONE, Priority.URGENT, 2L, List.of("backend")
        );

        TaskMapper.applyUpdate(request, task, newAssignee, newProject);

        assertThat(task.getName()).isEqualTo("New name");
        assertThat(task.getAssignee()).isEqualTo(newAssignee);
        assertThat(task.getStatus()).isEqualTo(Status.DONE);
        assertThat(task.getPriority()).isEqualTo(Priority.URGENT);
        assertThat(task.getLabels()).containsExactly("backend");
        assertThat(task.getProject()).isEqualTo(newProject);
    }
}
