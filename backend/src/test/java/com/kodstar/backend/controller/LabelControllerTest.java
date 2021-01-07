package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.service.LabelService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class LabelControllerTest {

  private Label label;

  @MockBean
  private LabelService labelService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(){
    this.label = new Label();
    label.setId(1L);
    label.setName("backend");
    label.setColor("fe642e");
  }

  @Test
  @DisplayName("Test createLabel Success")
  void createLabel() throws Exception {
    // Setup our mocked service
    when(labelService.saveLabelEntity(any())).thenReturn(label);

    // Execute the POST request
    mockMvc.perform(post("/label")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(label)))

            // Validate the response code and content type
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(label.getName())))
            .andExpect(jsonPath("$.color", is(label.getColor())));
  }

  @Test
  @DisplayName("Test labelFindById")
  void getLabelById() throws Exception {

    // Setup our mocked service
    when(labelService.findById(1L)).thenReturn(label);

    // Execute the GET request
    mockMvc.perform(get("/label/{id}", 1L))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(label.getName())))
            .andExpect(jsonPath("$.color", is(label.getColor())));
  }

  @Test
  void getLabels() {
  }

  @Test
  void updateLabel() {
  }

  @Test
  void deleteLabel() {
  }

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}