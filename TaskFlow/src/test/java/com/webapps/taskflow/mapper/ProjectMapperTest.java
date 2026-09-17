package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.project.ProjectCreateRequest;
import com.webapps.taskflow.dtos.project.ProjectResponse;
import com.webapps.taskflow.dtos.project.ProjectUpdateRequest;
import com.webapps.taskflow.entity.Project;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectMapperTest {

    @Test
    void toEntityMapsKeyNameAndDescription() {
        ProjectCreateRequest request = new ProjectCreateRequest("web", "Website Redesign", "Redesign da landing page");

        Project project = ProjectMapper.toEntity(request);

        assertThat(project.getKey()).isEqualTo("WEB");
        assertThat(project.getName()).isEqualTo("Website Redesign");
        assertThat(project.getDescription()).isEqualTo("Redesign da landing page");
    }

    @Test
    void toResponseMapsAllFields() {
        Project project = new Project("WEB", "Website Redesign", "Redesign da landing page");

        ProjectResponse response = ProjectMapper.toResponse(project);

        assertThat(response.id()).isEqualTo(project.getId());
        assertThat(response.key()).isEqualTo("WEB");
        assertThat(response.name()).isEqualTo("Website Redesign");
        assertThat(response.description()).isEqualTo("Redesign da landing page");
    }

    @Test
    void applyUpdateOverwritesNameAndDescription() {
        Project project = new Project("WEB", "Old Name", "Old description");
        ProjectUpdateRequest request = new ProjectUpdateRequest("New Name", "New description");

        ProjectMapper.applyUpdate(request, project);

        assertThat(project.getName()).isEqualTo("New Name");
        assertThat(project.getDescription()).isEqualTo("New description");
    }
}
