package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.model.entity.ProjectEntity;
import com.kodstar.backend.repository.ProjectRepository;
import com.kodstar.backend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectServiceImplTest {

  @Autowired
  private ProjectService projectService;

  @MockBean
  private ProjectRepository projectRepository;

  private Project project;

  @BeforeEach
  void setUp() {
    this.project = new Project();
    project.setId(1L);
    project.setName("project1");
    project.setDescription("This is a project");
    project.setState("open");
  }

  @Test
  @DisplayName("Test findById Success")
  void findById() {

    // Setup our mock repository
    ProjectEntity projectEntity = projectService.convertToEntity(project);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));

    // Execute the service call
    Project returnedProject = projectService.findById(1L);

    // Assert the response
    assertEquals(projectEntity.getName(),returnedProject.getName());
  }

  @Test
  @DisplayName("Test findById_NotFound")
  void findByIdNotFound() {

    // Setup our mock repository
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());

    // Assert the response
    assertThrows(EntityNotFoundException.class,()->projectService.findById(1L));

  }

  @Test
  void deleteProject() {
  }

  @Test
  void getAllProjects() {
  }

  @Test
  void saveProjectEntity() {
  }

  @Test
  void updateProjectEntity() {
  }
}