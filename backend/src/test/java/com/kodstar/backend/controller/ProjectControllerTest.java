package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.service.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectControllerTest {

  @MockBean
  private ProjectService projectService;

  @MockBean
  private IssueService issueService;

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
  @DisplayName("Test getProjectById Success")
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
  @DisplayName("Test getProjects Success")
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
  @DisplayName("Test createProject Success")
  void createProject() throws Exception {
    // Setup our mocked service
    when(projectService.saveProjectEntity(any())).thenReturn(project);

    // Execute the POST request
    mockMvc.perform(post("/project")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(project)))

            // Validate the response code and content type
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(project.getName())))
            .andExpect(jsonPath("$.description", is(project.getDescription())));
  }

  @Test
  @DisplayName("Test updateProject Success")
  void updateProject() throws Exception {

    // Execute the PUT request
    mockMvc.perform(put("/project/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(project))
            .accept(MediaType.APPLICATION_JSON))

            // Validate the response code
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Test deleteProject Success")
  void deleteProject() throws Exception {

    // Setup our mocked service
    doNothing().when(projectService).deleteProject(1L);

    // Execute the DELETE request
    mockMvc.perform(delete("/project/{id}",1))
            .andExpect(status().isNoContent());

    verify(projectService, times(1)).deleteProject(1L);
  }

  @Test
  @DisplayName("Test getIssuesByProjectId Success")
  void getIssuesByProjectId() throws Exception {

    //Setup our mocked service
    Issue issue1 = new Issue();
    issue1.setId(1L);
    issue1.setTitle("test");
    issue1.setDescription("test is important");
    issue1.setProjectId(2L);

    Issue issue2 = new Issue();
    issue2.setId(2L);
    issue2.setTitle("test2");
    issue2.setDescription("test2 is important");
    issue2.setProjectId(2L);

    when(issueService.findByProjectId(2L)).thenReturn(Arrays.asList(issue1, issue2));

    // Execute the GET request
    mockMvc.perform(get("/project/{id}/issues",2L))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is(issue1.getTitle())))
            .andExpect(jsonPath("$[0].description", is(issue1.getDescription())))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].title", is(issue2.getTitle())))
            .andExpect(jsonPath("$[1].description", is(issue2.getDescription())));
  }

  @Test
  @DisplayName("Test getIssuesByProjectId No Content")
  void getIssuesByProjectIdNoContent() throws Exception {

    // Setup our mocked service
    when(issueService.findByProjectId(any())).thenReturn(Collections.emptyList());

    // Execute the GET request
    mockMvc.perform(get("/project/{id}/issues", 1L))

            // Validate the response code
            .andExpect(status().isNoContent());
  }

  @Test
  void filterAndSort() {
  }


  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
