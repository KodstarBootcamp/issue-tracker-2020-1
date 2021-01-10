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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
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
  @DisplayName("Test getProjects")
  void getProjects() throws Exception{

    // Setup our mocked service
    Project project1 = new Project();
    project1.setId(2L);
    project1.setName("project2");
    project1.setDescription("This is a project");
    project1.setState("open");

    when(projectService.getAllProjects()).thenReturn(Arrays.asList(project, project1));

    // Execute the GET request
    mockMvc.perform(get("/projects"))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is(project.getName())))
            .andExpect(jsonPath("$[0].description", is(project.getDescription())))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].name", is(project1.getName())))
            .andExpect(jsonPath("$[1].description", is(project1.getDescription())));
  }

  @Test
  @DisplayName("Test getProjects No Content")
  void getProjectsNoContent() throws Exception{

    // Setup our mocked service
    when(projectService.getAllProjects()).thenReturn(Collections.emptyList());

    // Execute the GET request
    mockMvc.perform(get("/projects"))

            // Validate the response code
            .andExpect(status().isNoContent());
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