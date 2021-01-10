package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

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
  void getProjectById() {
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