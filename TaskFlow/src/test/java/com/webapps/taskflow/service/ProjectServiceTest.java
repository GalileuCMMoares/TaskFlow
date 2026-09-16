package com.webapps.taskflow.service;

import com.webapps.taskflow.dtos.project.ProjectUpdateRequest;
import com.webapps.taskflow.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void updateThrowsNotFoundWhenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        ProjectUpdateRequest request = new ProjectUpdateRequest("New Name", "New description");

        assertThatThrownBy(() -> projectService.update(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Project not found");
    }

    @Test
    void deleteThrowsNotFoundWhenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Project not found");
    }
}
