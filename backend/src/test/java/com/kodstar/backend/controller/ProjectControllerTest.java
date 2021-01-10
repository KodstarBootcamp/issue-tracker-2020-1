package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

  @MockBean
  private ProjectService projectService;

  @Autowired
  private MockMvc mockMvc;

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
  @DisplayName("Test getProjectById")
  void getProjectById() throws Exception {

    // Setup our mocked service
    when(projectService.findById(1L)).thenReturn(project);

    // Execute the GET request
    mockMvc.perform(get("/project/{id}", 1))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(project.getName())))
            .andExpect(jsonPath("$.description", is(project.getDescription())));
  }

  @Test
  void getProjects() {
  }

  @Test
  void createProject() {
  }

  @Test
  void updateProject() {
  }

  @Test
  void deleteProject() {
  }

  @Test
  void getIssuesByProjectId() {
  }

  @Test
  void filterAndSort() {
  }
}